package io.github.sergeyboboshko.ceppb.details

import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity
import androidx.compose.runtime.Composable



import io.github.sergeyboboshko.ceppb.daemons.DetailsPaymentDocumentsHelper
import io.github.sergeyboboshko.ceppb.daemons.MyGlobalVariables
import io.github.sergeyboboshko.ceppb.documents.DocSubsidy
import io.github.sergeyboboshko.ceppb.documents.DocSubsidyExt
import io.github.sergeyboboshko.ceppb.documents.DocUtilityChargeExt
import io.github.sergeyboboshko.ceppb.references.RefMeterZones
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

@CeGenerator(type = GeneratorType.Details, label = "The Details Subsidy")
@CeEntity(
    tableName = "details_subsidy")
@CeCreateTable("details_subsidy")
class DetailsSubsidy(
    
    override var id: Long,
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
        onChange = "DetailsSubsidyHelper.onUtilityEdited"
    )
    var utilityId: Long,
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
    var meterId: Long,
    @CeField(
        related = true,
        relatedEntityClass = RefMeterZones::class,
        extName = "zone",
        type = FieldTypeHelper.SELECT,
        label = "@@zone_label",
        placeHolder = "@@zone_placeholder",
        positionOnForm = 1,
        useForOrder = true
    )
    var zoneId:Long,
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
        condition = "io.github.sergeyboboshko.ceppb.daemons.DetailsPaymentDocumentsHelper.meterReadingCondition",
        onEndEditing= "DetailsSubsidyHelper.onMeterREdited"
    )
    var meterR: Double
) : CommonDetailsEntity(id, parentId) {
    
    @CeField(
        placeHolder = "Last reading:",
        renderInList = false,
        renderInAddEdit = true,
        type = FieldTypeHelper.COMPOSABLE,
        customComposable = "DetailsSubsidyHelper.LastReading"
        //customComposable = "DetailsUtilityChargeHelper.LastReading"
    )
    @CeIgnore
    var lastReading: String = ""
}

object DetailsSubsidyHelper {
    fun onUtilityEdited(currentValue: Any, vm: _BaseFormVM, ui: BaseUI){
        DetailsPaymentDocumentsHelper.onUtilityEdited(currentValue,vm, ui, AppGlobalCE.docSubsidyViewModel as _BaseFormVM)
    }

    @Composable
    fun LastReading(vm: _BaseFormVM, formType: FormType? = null) {
        DetailsPaymentDocumentsHelper.LastReading(vm,formType, AppGlobalCE.docSubsidyViewModel as _BaseFormVM)
    }

    fun onMeterREdited (currentValue: Any, vm: _BaseFormVM, ui: BaseUI){
        val period = (AppGlobalCE.docSubsidyViewModel.anyItem as DocSubsidyExt).link.date
        MyGlobalVariables.paymentDocumentsHelperWiewModel.gefAmountCount(period,currentValue.toString().toFloat(), vm, null)
    }
}