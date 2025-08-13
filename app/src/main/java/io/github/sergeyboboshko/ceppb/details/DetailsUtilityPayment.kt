package io.github.sergeyboboshko.ceppb.details

import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity
import androidx.compose.runtime.Composable




import io.github.sergeyboboshko.ceppb.daemons.DetailsPaymentDocumentsHelper

import io.github.sergeyboboshko.ceppb.daemons.DetailsPaymentHelperClass
import io.github.sergeyboboshko.ceppb.documents.DocUtilityPayment
import io.github.sergeyboboshko.ceppb.references.RefMeters
import io.github.sergeyboboshko.ceppb.references.RefUtilitiseEntity
import io.github.sergeyboboshko.composeentity.daemons.BaseUI
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons.FormType
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsEntity
import io.github.sergeyboboshko.composeentity_ksp.AppGlobalCE
import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentity_ksp.base.CeIgnore

@CeGenerator(type = GeneratorType.Details, label = "The Details Payment")

@CeEntity(
    tableName = "details_utility_payment")
@CeCreateTable("details_utility_payment")
class DetailsUtilityPayment(
    
    override var id: Long,
    @CeField(
        related = true,
        relatedEntityClass = DocUtilityPayment::class,
        renderInAddEdit = false,
        renderInList = false,
        renderInView = false
    )
    override var parentId: Long,
    @CeField(
        related = true,
        relatedEntityClass = RefUtilitiseEntity::class,
        extName = "utility",
        type = FieldTypeHelper.SELECT,
        label = "@@utility_label",
        placeHolder = "@@utility_placeholder",
        positionOnForm = 1,
        useForOrder = true,
        //onChange = "DetailsUtilityPaymentHelper.onUtilityEdited"
        onChange = "DetailsUtilityPaymentHelper.onUtilityEdited"
    )
    override var utilityId: Long,
    @CeField(
        related = true,
        relatedEntityClass = RefMeters::class,
        extName = "meter",
        type = FieldTypeHelper.SELECT,
        label = "@@meter_label",
        placeHolder = "@@meter_placeholder",
        positionOnForm = 1,
        useForOrder = true
    )
    override var meterId: Long,
    @CeField(
        label = "@@amount_label",
        placeHolder = "@@amount_placeholder",
        type = FieldTypeHelper.DECIMAL
    )
    var amount: Double,
    @CeField(
        label = "@@describe_label",
        placeHolder = "@@describe_placeholder",
        type = FieldTypeHelper.TEXT
    )
    var describe: String,

    
    @CeField(
        label = "@@meter_reading_label",
        placeHolder = "@@meter_reading_placeholder",
        type = FieldTypeHelper.DECIMAL,
        condition = "io.github.sergeyboboshko.ceppb.daemons.DetailsPaymentDocumentsHelper.meterReadingCondition"
    )
    var meterR: Double
) : CommonDetailsEntity(id, parentId), DetailsPaymentHelperClass{
    
    @CeField(
        placeHolder = "Last reading:",
        renderInList = false,
        renderInAddEdit = true,
        type = FieldTypeHelper.COMPOSABLE,
        //customComposable = "io.github.sergeyboboshko.ceppb.daemons.DetailsPaymentDocumentsHelper.LastReading"
        customComposable = "DetailsUtilityPaymentHelper.LastReading"
    )
    @CeIgnore
    var lastReading: String = ""
}
object DetailsUtilityPaymentHelper{
    fun onUtilityEdited(currentValue: Any, vm: _BaseFormVM, ui: BaseUI){
        DetailsPaymentDocumentsHelper.onUtilityEdited(currentValue,vm, ui,AppGlobalCE.docUtilityPaymentViewModel as _BaseFormVM)
    }


    @Composable
    fun LastReading(vm: _BaseFormVM, formType: FormType? = null) {
        DetailsPaymentDocumentsHelper.LastReading(vm,formType,AppGlobalCE.docUtilityPaymentViewModel as _BaseFormVM)
    }
}
