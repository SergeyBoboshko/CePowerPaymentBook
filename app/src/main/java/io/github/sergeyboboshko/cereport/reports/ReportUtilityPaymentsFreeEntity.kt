package io.github.sergeyboboshko.cereport.reports

import android.database.Cursor
import android.location.Address
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.cereport.R
import io.github.sergeyboboshko.cereport.free.FreeReportUtilityPaymentsEntityResult
import io.github.sergeyboboshko.cereport.references.RefAddressesEntity
import io.github.sergeyboboshko.cereport.references.RefAddressesEntityUI
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons._BaseDescribeFormElement
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.daemons.emptyCursor
import io.github.sergeyboboshko.composeentity.daemons.getEndOfDay
import io.github.sergeyboboshko.composeentity.references.base.RefUI
import io.github.sergeyboboshko.composeentity.reports.base.ReportEntity

import io.github.sergeyboboshko.composeentity_ksp.base.CeReport
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeMigrationEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentity_ksp.entity.GenerationLevel


//створюємо єнтіті для зберігання налаштувань фильтра. Фільтр буде включати в себе адресу та розмір залишку
@Entity(tableName = "rep_utilitypayments_free_settings")
@CeGenerator(type = GeneratorType.ReportCursor,label="Free Grouping Balance/Overpayment", generationLevel = GenerationLevel.UI, hasDetails = true, detailsEntityClass = FreeReportUtilityPaymentsEntityResult::class)
//@CeMigrationEntity(14)
@CeReport(resultEntity = FreeReportUtilityPaymentsEntityResult::class,
    query = """SELECT MAX(period) AS period, addressId, utilityId,Address,Utility,amount FROM
        (SELECT period, addressId, utilityId,
        ref_addresses.name AS Address,
        ref_utilities.name AS Utility,
        SUM(CASE WHEN transactionType='EXPENSE' THEN -amount ELSE amount END) AS amount
        FROM areg_payments
        LEFT JOIN ref_addresses
        ON areg_payments.addressId = ref_addresses.id
        LEFT JOIN ref_utilities ON areg_payments.utilityId = ref_utilities.id 
        GROUP BY 
        addressId,
        utilityId ) as tab 
    """,
    groups = """ 
        addressId, utilityId,Address,Utility,amount""")

data class ReportUtilityPaymentsFreeEntity(
    @PrimaryKey(autoGenerate = true) override var id: Long,
    override var name:String,
    @ColumnInfo(defaultValue = "0")
    @CeField(type = FieldTypeHelper.DATE,label="@@period", placeHolder = "@@period_label", onChange = "onPeriodEndEditing", wrapInFilter = true)
    var period:Long,
    @CeField(type = FieldTypeHelper.TEXT,label="@@describe_label", placeHolder = "@@describe_placeholder")
    override var describe:String,
    @CeField(related = true,label="@@address_label", placeHolder = "@@address_placeholder", relatedEntityClass = RefAddressesEntity::class
        , type = FieldTypeHelper.SELECT, wrapInFilter = true)
    var addressId:Long,
    @CeField(type = FieldTypeHelper.DECIMAL,label="@@amount_label", placeHolder = "@@amount_placeholder", wrapInFilter = true)
    var amount:Double,
    @CeField(type = FieldTypeHelper.TEXT, label = "Conditions", placeHolder = "", renderInAddEdit = true)
    override var conditions: String

): ReportEntity(id,"addressId","amount",name,describe)

fun onPeriodEndEditing (currentValue: Any, vm: _BaseFormVM, ui: ReportUtilityPaymentsFreeEntityUI){
    //{{{norew
    val endOfDay = getEndOfDay(currentValue as Long)
    vm.updateField("period",endOfDay.toString())
    //}}}
}