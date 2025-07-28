package io.github.sergeyboboshko.cereport.details

import android.os.Parcelable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.cereport.daemons.DetailsPaymentDocumentsHelper
import io.github.sergeyboboshko.cereport.daemons.DocsPayment
import io.github.sergeyboboshko.cereport.documents.DocUtilityCharge
import io.github.sergeyboboshko.cereport.documents.DocUtilityChargeExt
import io.github.sergeyboboshko.cereport.references.RefMeters
import io.github.sergeyboboshko.cereport.references.RefUtilitiseEntity
import io.github.sergeyboboshko.composeentity.daemons.BaseUI
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons.FieldValidator
import io.github.sergeyboboshko.composeentity.daemons.FormType
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsEntity
import io.github.sergeyboboshko.composeentity_ksp.AppGlobalCE
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@CeGenerator(type = GeneratorType.Details, label = "The Details Charge")
@Parcelize
@Entity(
    tableName = "details_utility_charge",
    foreignKeys = [
        ForeignKey(
            entity = DocUtilityCharge::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
//@CeMigrationEntity(migrationVersion = 3)
class DetailsUtilityCharge(
    @PrimaryKey(autoGenerate = true)
    override var id: Long,
    @CeField(
        related = true,
        relatedEntityClass = DocUtilityCharge::class,
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
        onChange = "DetailsUtilityChargeHelper.onUtilityEdited"
        //onChange = "DetailsUtilityChargeHelper.onUtilityEdited"
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
    @CeField(label = "@@amount_label", placeHolder = "@@amount_placeholder", type = FieldTypeHelper.DECIMAL)
    var amount: Double,
    @CeField(label = "@@describe_label", placeHolder = "@@describe_placeholder", type = FieldTypeHelper.TEXT)
    var describe: String,

    @ColumnInfo(defaultValue = "0")
    @CeField(label = "@@meter_reading_label", placeHolder = "@@meter_reading_placeholder",
        type = FieldTypeHelper.DECIMAL,
        condition = "io.github.sergeyboboshko.cereport.daemons.DetailsPaymentDocumentsHelper.meterReadingCondition"
        //condition = "DetailsUtilityChargeHelper.meterReadingCondition"
    )
    var meterR: Double

    // @ColumnInfo(defaultValue = "0")
    // @CeField(label = "@@meter_reading_label", placeHolder = "@@meter_reading_placeholder", type = FieldTypeHelper.DECIMAL)
    // var meterReading: Double
) : CommonDetailsEntity(id, parentId),  Parcelable {

    @Ignore
    @CeField(
        placeHolder = "Last reading:",
        renderInList = false,
        renderInAddEdit = true,
        type = FieldTypeHelper.COMPOSABLE,
        customComposable = "DetailsUtilityChargeHelper.LastReading"
        //customComposable = "DetailsUtilityChargeHelper.LastReading"
    )
    var lastReading: String = ""
}

object DetailsUtilityChargeHelper {
    fun onUtilityEdited(currentValue: Any, vm: _BaseFormVM, ui: BaseUI){
        DetailsPaymentDocumentsHelper.onUtilityEdited(currentValue,vm, ui,AppGlobalCE.docUtilityChargeViewModel as _BaseFormVM)
    }

    @Composable
    fun LastReading(vm: _BaseFormVM, formType: FormType? = null) {
        DetailsPaymentDocumentsHelper.LastReading(vm,formType,AppGlobalCE.docUtilityChargeViewModel as _BaseFormVM)
    }
}
