package io.github.sergeyboboshko.cereport.documents

import android.os.Parcelable
import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.cereport.MyApplication1
import io.github.sergeyboboshko.cereport.accumulationregisters.ARegPayments
import io.github.sergeyboboshko.cereport.details.DetailsUtilityCharge
import io.github.sergeyboboshko.cereport.references.RefAddressesEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons.FormType
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.documents.base.CommonDocumentEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeDocumentDescriber

import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import kotlinx.parcelize.Parcelize
import io.github.sergeyboboshko.cereport.alerts.*

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity_ksp.AppGlobalCE
import kotlinx.coroutines.launch

@Parcelize
@Entity(tableName = "doc_utility_charge")
@CeGenerator(
    type = GeneratorType.Document, label = "Document Utility Charge",
    hasDetails = true, detailsEntityClass = DetailsUtilityCharge::class
)
//@CeMigrationEntity(migrationVersion = 2)
@CeDocumentDescriber(
    accumulationRegistersExpense = [ARegPayments::class],
    documentType = DocTypes.DocUtilityCharge
)
data class DocUtilityCharge(
    @PrimaryKey(autoGenerate = true)
    override var id: Long,
    override var date: Long,
    override var number: Long,
    override var isPosted: Boolean,
    override var isMarkedForDeletion: Boolean,
    //Address, Describe
    @CeField(
        related = true,
        type = FieldTypeHelper.SELECT,
        relatedEntityClass = RefAddressesEntity::class,
        label = "@@address_label",
        placeHolder = "@@address_placeholder"
    )
    var addressId: Long,
    @CeField(
        label = "@@describe_label",
        placeHolder = "@@describe_placeholder",
        type = FieldTypeHelper.TEXT
    )
    var describe: String
) : CommonDocumentEntity(
    id, date, number, isPosted, isMarkedForDeletion
), Parcelable {
    @Ignore
    @CeField(
        label = "-",
        type = FieldTypeHelper.COMPOSABLE,
        customComposable = "UtilityChargeHelper.FillDetails",
        renderInList = false,
        renderInAddEdit = false
    )
    var fillDetails: String = ""
}

object UtilityChargeHelper {
    @Composable
    fun FillDetails(vm: _BaseFormVM, formType: FormType? = null) {
        var showDialogue by remember { mutableStateOf(false) }
        val currentDoc = vm.anyItem as DocUtilityChargeExt
        var refresher by remember { mutableStateOf(true) }
        LaunchedEffect(refresher) {
            AppGlobalCE.detailsUtilityChargeViewModel.refreshAll()
            AppGlobalCE.detailsUtilityChargeViewModel.refreshAllExt()
       }
        if (showDialogue) {
            CleanAndRefillDialodue(
                onConfirm = {
                    //Toast.makeText(MyApplication1.appContext,"We Filling Details",Toast.LENGTH_SHORT).show()
                    val sqlDelete = "DELETE FROM details_utility_charge WHERE parentId = ?"
                    val sqlInsert = """
                        INSERT INTO details_utility_charge (
                                parentId, utilityId, meterId, amount, describe, meterR
                            )
                            SELECT ?, utilityId, meterId, 0.0, describe, 0.0
                            FROM ref_adress_details
                         WHERE parentId = ?
                        """.trimIndent()

                    AppGlobalCE.docUtilityChargeViewModel.viewModelScope.launch {
                        AppGlobalCE.docUtilityChargeViewModel.repository.dao.performDelete(
                            androidx.sqlite.db.SimpleSQLiteQuery(
                                sqlDelete,
                                arrayOf(currentDoc.link.id)
                            )
                        )
                        AppGlobalCE.docUtilityChargeViewModel.repository.dao.performDelete(
                            androidx.sqlite.db.SimpleSQLiteQuery(
                                sqlInsert,
                                arrayOf(currentDoc.link.id, currentDoc.link.addressId)
                            )
                        )
//
                        refresher=!refresher
                    }

                    showDialogue = false

                },
                onDismiss = {
                    Toast.makeText(MyApplication1.appContext, "DISMISS", Toast.LENGTH_SHORT).show()
                    showDialogue = false
                }
            )
        } else {
            TextButton(onClick = { showDialogue = true }) {
                Text("Fill Utilities By Address")
            }
        }
    }
}
