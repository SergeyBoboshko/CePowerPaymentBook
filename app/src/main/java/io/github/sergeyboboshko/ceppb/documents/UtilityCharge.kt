package io.github.sergeyboboshko.ceppb.documents

import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity
import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


import io.github.sergeyboboshko.ceppb.MyApplication1
import io.github.sergeyboboshko.ceppb.accumulationregisters.ARegPayments
import io.github.sergeyboboshko.ceppb.details.DetailsUtilityCharge
import io.github.sergeyboboshko.ceppb.references.RefAddressesEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons.FormType
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.documents.base.CommonDocumentEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeDocumentDescriber

import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.ceppb.alerts.*

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import io.github.sergeyboboshko.ceppb.daemons.DocsPayment
import io.github.sergeyboboshko.ceppb.daemons.MyGlobalVariables
import io.github.sergeyboboshko.composeentity.documents.base.CommonDocumentExtEntity
import io.github.sergeyboboshko.composeentity_ksp.AppGlobalCE
import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable
import io.github.sergeyboboshko.composeentity_ksp.base.CeIgnore
import kotlinx.coroutines.launch


@CeEntity(tableName = "doc_utility_charge")
@CeGenerator(
    type = GeneratorType.Document, label = "Document Utility Charge",
    hasDetails = true, detailsEntityClass = DetailsUtilityCharge::class
)
@CeCreateTable("doc_utility_charge")
@CeDocumentDescriber(
    accumulationRegistersExpense = [ARegPayments::class],
    documentType = DocTypes.DocUtilityCharge
)
data class DocUtilityCharge(
    
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
    override var addressId: Long,
    @CeField(
        label = "@@describe_label",
        placeHolder = "@@describe_placeholder",
        type = FieldTypeHelper.TEXT
    )
    var describe: String
) : CommonDocumentEntity(
    id, date, number, isPosted, isMarkedForDeletion
), DocsPayment{
    
    @CeField(
        label = "-",
        type = FieldTypeHelper.COMPOSABLE,
        customComposable = "UtilityChargeHelper.FillDetails",
        renderInList = false,
        renderInAddEdit = false
    )
    @CeIgnore
    var fillDetails: String = ""
}

object UtilityChargeHelper {
    @Composable
    fun FillDetails(vm: _BaseFormVM, formType: FormType? = null) {
        var showDialogue by remember { mutableStateOf(false) }
        val currentDoc = vm.anyItem as DocUtilityChargeExt
        var refresher = MyGlobalVariables.paymentDocumentsHelperWiewModel.refresher.collectAsState()
        LaunchedEffect(refresher.value) {
            AppGlobalCE.detailsUtilityChargeViewModel.refreshAll()
            AppGlobalCE.detailsUtilityChargeViewModel.refreshAllExt()
       }
        if (showDialogue) {
            CleanAndRefillDialodue(
                onConfirm = {
                    MyGlobalVariables.paymentDocumentsHelperWiewModel.fillDetails(
                        detailsTableName = "details_utility_charge",
                        currentDoc = currentDoc as CommonDocumentExtEntity<CommonDocumentEntity>
                    )
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
