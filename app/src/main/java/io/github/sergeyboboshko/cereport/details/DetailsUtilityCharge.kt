package io.github.sergeyboboshko.cereport.details

import android.os.Parcelable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.cereport.R
import io.github.sergeyboboshko.cereport.documents.DocUtilityCharge
import io.github.sergeyboboshko.cereport.documents.DocUtilityChargeExt
import io.github.sergeyboboshko.cereport.references.RefAddressesEntity
import io.github.sergeyboboshko.cereport.references.RefAddressesEntityUI
import io.github.sergeyboboshko.cereport.references.RefMeters
import io.github.sergeyboboshko.cereport.references.RefUtilitiseEntity
import io.github.sergeyboboshko.cereport.references.RefUtilitiseEntityExt
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons.FieldValidator
import io.github.sergeyboboshko.composeentity.daemons.FormType
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsEntity
import io.github.sergeyboboshko.composeentity_ksp.AppGlobalCE
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeMigrationEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.temporal.TemporalAmount

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
        type = FieldTypeHelper.DECIMAL,condition = "DetailsUtilityChargeHelper.meterReadingCondition")
    var meterR: Double

    // @ColumnInfo(defaultValue = "0")
    // @CeField(label = "@@meter_reading_label", placeHolder = "@@meter_reading_placeholder", type = FieldTypeHelper.DECIMAL)
    // var meterReading: Double
) : CommonDetailsEntity(id, parentId), Parcelable {

    @Ignore
    @CeField(
        placeHolder = "Last reading:",
        renderInList = false,
        renderInAddEdit = true,
        type = FieldTypeHelper.COMPOSABLE,
        customComposable = "DetailsUtilityChargeHelper.LastReading"
    )
    var lastReading: String = ""
}

object DetailsUtilityChargeHelper {
    var lastMeterReadings:Float = 0f
    fun meterReadingCondition():FieldValidator {
        return object : FieldValidator {
            override var errorMessage = "Meter reading must be equals or biggest then previous one"
            override fun isValid(value: Any):Boolean{
                val a= value.toString().toFloatOrNull()?:0f
                return a>=lastMeterReadings
            }
        }
    }

    fun onUtilityEdited(currentValue: Any, vm: _BaseFormVM, ui: DetailsUtilityChargeUI) {
        val current = AppGlobalCE.docUtilityChargeViewModel.anyItem as DocUtilityChargeExt // take addressId from current document rendering on screen
        val addrID = current.address?.id
        // find the meter linked to utility and fill meter field
        val sqlText = "SELECT * FROM ref_adress_details WHERE utilityId = ? AND parentId=?" // get dependency between utility and meter in address details table
        val params = arrayOf((currentValue as RefUtilitiseEntity).id, addrID) // current value contents id of object represents utility in table
        // if field is related, currentValue is not of simple types, but Ext class of the current entity
        AppGlobalCE.forSQLViewModel.viewModelScope.launch(Dispatchers.IO) {
            val cursor = AppGlobalCE.forSQLViewModel.repository.generateResultFromReport(sqlText, params as Array<Any>)
            if (cursor.moveToFirst()) {
                val meterIdIndex = cursor.getColumnIndex("meterId")
                if (meterIdIndex != -1) {
                    val meterId = cursor.getString(meterIdIndex)
                    vm.updateField("meterId", meterId) // set new value linked to Utility
                    vm.updateView() // tell form VM to update elements on itself
                }
            }
            cursor.close() // don't forget to close the cursor
        }
    }



    @Composable
    fun LastReading(vm: _BaseFormVM, formType: FormType? = null) {
        val currElement = vm.anyItem as? DetailsUtilityChargeExt
        val parent = currElement?.parent
        val lastReading = remember { mutableStateOf<Float?>(null) }

        if (currElement == null || parent == null) {
            Text("No previous readings")
            return
        }

        // Function to update the value
        fun updateReading(utilityId: Long?, meterId: Long?) {
            val query =
                "SELECT MAX(meterR) as lastReading FROM areg_payments WHERE addressId=? AND utilityId=? AND meterId=?"
            val params = arrayOf(parent.addressId, utilityId ?: 0L, meterId ?: 0L)

            AppGlobalCE.forSQLViewModel.viewModelScope.launch(Dispatchers.IO) {
                val cursor = AppGlobalCE.forSQLViewModel.repository.generateResultFromReport(
                    query, params as Array<Any>
                )
                cursor.use {
                    if (it.moveToFirst()) {
                        val value = it.getFloat(it.getColumnIndexOrThrow("lastReading"))
                        lastReading.value = value
                        lastMeterReadings = value
                    } else {
                        lastReading.value = null
                        lastMeterReadings = 0f
                    }
                }
            }
        }

        // Observe changes only in Add/Edit mode
        if (formType == null) {
            var utility = vm.formData["utilityId"] ?: "0"
            var meter = vm.formData["meterId"] ?: "0"

            if (utility.equals("")) utility = "0"
            if (meter.equals("")) meter = "0"

            LaunchedEffect(utility, meter) {
                val utilityId = utility?.toLongOrNull()
                val meterId = meter?.toLongOrNull()
                updateReading(utilityId, meterId)
            }
        } else {
            LaunchedEffect(currElement.meter?.id) {
                val utilityId = currElement.utility?.id
                val meterId = currElement.meter?.id
                updateReading(utilityId, meterId)
            }
        }

        if (lastReading.value != null) {
            Text("Last reading: ${lastReading.value}")
        } else {
            Text("No previous readings")
        }
    }
}
