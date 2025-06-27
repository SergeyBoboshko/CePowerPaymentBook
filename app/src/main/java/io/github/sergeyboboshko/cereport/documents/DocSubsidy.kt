package io.github.sergeyboboshko.cereport.documents

import android.os.Parcelable
import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.cereport.MyApplication1
import io.github.sergeyboboshko.cereport.accumulationregisters.ARegPayments
import io.github.sergeyboboshko.cereport.alerts.CleanAndRefillDialodue
import io.github.sergeyboboshko.cereport.details.DetailsSubsidy
import io.github.sergeyboboshko.cereport.details.DetailsUtilityCharge
import io.github.sergeyboboshko.cereport.references.RefAddressesEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons.FormType
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.daemons.mainCustomStack
import io.github.sergeyboboshko.composeentity.details.base._DetailsViewModel
import io.github.sergeyboboshko.composeentity.documents.base.CommonDocumentEntity
import io.github.sergeyboboshko.composeentity.documents.base.DocUI
import io.github.sergeyboboshko.composeentity_ksp.AppGlobalCE
import io.github.sergeyboboshko.composeentity_ksp.base.CeDocumentDescriber
import io.github.sergeyboboshko.composeentity_ksp.base.FormFieldCE
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.MigrationEntityCE
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "doc_subsidy")
@ObjectGeneratorCE(
    type = GeneratorType.Document, label = "Document Subsidy Payments",
    hasDetails = true, detailsEntityClass = DetailsSubsidy::class
)
@MigrationEntityCE(migrationVersion = 9)
@CeDocumentDescriber(
    accumulationRegistersIncome = [ARegPayments::class],
    documentType = DocTypes.DocSubsidy
)
data class DocSubsidy(
    @PrimaryKey(autoGenerate = true)
    override var id: Long,
    override var date: Long,
    override var number: Long,
    override var isPosted: Boolean,
    override var isMarkedForDeletion: Boolean,
    //Address, Describe
    @FormFieldCE(
        related = true,
        type = FieldTypeHelper.SELECT,
        relatedEntityClass = RefAddressesEntity::class,
        label = "@@address_label",
        placeHolder = "@@address_placeholder"
    )
    var addressId: Long,
    @FormFieldCE(
        label = "@@describe_label",
        placeHolder = "@@describe_placeholder",
        type = FieldTypeHelper.TEXT
    )
    var describe: String,
    @FormFieldCE(
        label = "URL",
        placeHolder = "Input URL",
        type = FieldTypeHelper.TEXT
    )
    @ColumnInfo(defaultValue = "")
    var url: String
) : CommonDocumentEntity(
    id, date, number, isPosted, isMarkedForDeletion
), Parcelable {
    @Ignore
    @FormFieldCE(
        label = "-",
        type = FieldTypeHelper.COMPOSABLE,
        customComposable = "UtilitySubsidyHelper.FillDetails",
        renderInList = false,
        renderInAddEdit = false
    )
    var fillDetails: String = ""
}

object UtilitySubsidyHelper {
    @Composable
    fun FillDetails(vm: _BaseFormVM, formType: FormType? = null) {
        var showDialogue by remember { mutableStateOf(false) }
        val currentDoc = vm.anyItem as DocSubsidyExt
        var refresher by remember { mutableStateOf(true) }
        LaunchedEffect(refresher) {
            AppGlobalCE.detailsSubsidyViewModel.refreshAll()
            AppGlobalCE.detailsSubsidyViewModel.refreshAllExt()
        }

        if (showDialogue) {
            CleanAndRefillDialodue(
                onConfirm = {
                    //Toast.makeText(MyApplication1.appContext,"We Filling Details",Toast.LENGTH_SHORT).show()
                    val sqlDelete = "DELETE FROM details_subsidy WHERE parentId = ?"
                    val sqlInsert = """
                        INSERT INTO details_subsidy (
                                parentId, utilityId, meterId, amount, describe, meterR
                            )
                            SELECT ?, utilityId, meterId, 0.0, "auto", 0.0
                            FROM ref_adress_details
                         WHERE parentId = ?
                        """.trimIndent()

                    AppGlobalCE.forSQLViewModel.viewModelScope.launch {
                        AppGlobalCE.forSQLViewModel.repository.execSQL(
                            sqlDelete,
                            arrayOf(currentDoc.link.id)
                        )
                        AppGlobalCE.forSQLViewModel.repository.execSQL(
                            sqlInsert,
                            arrayOf(currentDoc.link.id, currentDoc.link.addressId)
                        )
                        //!Impotant! repost doc   !Impotant!   !Impotant!   !Impotant!
                        val accumUIs = (mainCustomStack.peek() as DocUI).regs
                        val infoUIs = (mainCustomStack.peek() as DocUI).infoRegs
                        AppGlobalCE.docSubsidyViewModel.onPost(
                            regs = accumUIs,
                            infoRegs = infoUIs,
                            AppGlobalCE.detailsUtilityChargeViewModel as _DetailsViewModel)
                        //-----------------------------------------------------------------
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