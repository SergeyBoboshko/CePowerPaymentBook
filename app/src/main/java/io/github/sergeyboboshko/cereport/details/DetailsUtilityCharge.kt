package io.github.sergeyboboshko.cereport.details

import android.os.Parcelable
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.cereport.documents.DocUtilityCharge
import io.github.sergeyboboshko.cereport.documents.DocUtilityChargeExt
import io.github.sergeyboboshko.cereport.references.RefAddressesEntity
import io.github.sergeyboboshko.cereport.references.RefAddressesEntityUI
import io.github.sergeyboboshko.cereport.references.RefMeters
import io.github.sergeyboboshko.cereport.references.RefUtilitiseEntity
import io.github.sergeyboboshko.cereport.references.RefUtilitiseEntityExt
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
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
@Entity(tableName = "details_utility_charge",
    foreignKeys = [
        ForeignKey(
            entity = DocUtilityCharge::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
////@CeMigrationEntity(migrationVersion = 3)
class DetailsUtilityCharge(
    @PrimaryKey(autoGenerate = true)
    override var id: Long,
    @CeField (related = true, relatedEntityClass = DocUtilityCharge::class, renderInAddEdit = false, renderInList = false, renderInView = false)
    override var parentId: Long,
    @CeField(related = true, relatedEntityClass = RefUtilitiseEntity::class, extName = "utility", type = FieldTypeHelper.SELECT
        , label = "@@utility_label", placeHolder = "@@utility_placeholder", positionOnForm = 1, useForOrder = true,
        onChange = "DetailsUtilityChargeHelper.onUtilityEdited")
    var utilityId:Long,
    @CeField(related = true, relatedEntityClass = RefMeters::class,
        extName = "meter", type = FieldTypeHelper.SELECT
        , label = "@@meter_label", placeHolder = "@@meter_placeholder",
        positionOnForm = 1, useForOrder = true)
    var meterId:Long,
    @CeField(label = "@@amount_label", placeHolder = "@@amount_placeholder",type= FieldTypeHelper.DECIMAL)
    var amount: Double,
    @CeField(label = "@@describe_label", placeHolder = "@@describe_placeholder",type= FieldTypeHelper.TEXT)
    var describe:String,

    @ColumnInfo(defaultValue = "0")
    @CeField(label = "@@meter_reading_label", placeHolder = "@@meter_reading_placeholder",type= FieldTypeHelper.DECIMAL)
    var meterR:Double

//    @ColumnInfo(defaultValue = "0")
//    @CeField(label = "@@meter_reading_label", placeHolder = "@@meter_reading_placeholder",type= FieldTypeHelper.DECIMAL)
//    var meterReading:Double
): CommonDetailsEntity(id,parentId), Parcelable {

}

object DetailsUtilityChargeHelper{
    fun onUtilityEdited(currentValue: Any, vm: _BaseFormVM, ui: DetailsUtilityChargeUI){
        val current = AppGlobalCE.docUtilityChargeViewModel.anyItem as DocUtilityChargeExt//take addressId from current document rendering on screen
        val addrID = current.address?.id
        //find the meter linked to utility and fill meter field
        val sqlText = "SELECT * FROM ref_adress_details WHERE utilityId = ? AND parentId=?" //get dependency between utility and meter in address details table
        val params = arrayOf((currentValue as RefUtilitiseEntity).id,addrID)//current value contents id of object repredents utility in table
        //if field is related, currentValue is not of simple types, but Ext class of the current entity
        AppGlobalCE.forSQLViewModel.viewModelScope.launch(Dispatchers.IO) {
            val cursor = AppGlobalCE.forSQLViewModel.repository.generateResultFromReport(sqlText, params as Array<Any>)
            if (cursor.moveToFirst()) {
                val meterIdIndex = cursor.getColumnIndex("meterId")
                if (meterIdIndex != -1) {
                    val meterId = cursor.getString(meterIdIndex)
                    vm.updateField("meterId",meterId)//set new value linked to Utility
                    vm.updateView()//say to form VM update elements on itself
                }
            }
            cursor.close() // не забудь закрити курсор
        }
    }
}