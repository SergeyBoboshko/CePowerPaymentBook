package io.github.sergeyboboshko.cereport.reports

import android.database.Cursor
import android.location.Address
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.cereport.R
import io.github.sergeyboboshko.cereport.free.FreeReportUtilityPaymentsEntityResult
import io.github.sergeyboboshko.cereport.references.RefAddressesEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons._BaseDescribeFormElement
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.daemons.emptyCursor
import io.github.sergeyboboshko.composeentity.references.base.RefUI
import io.github.sergeyboboshko.composeentity.reports.base.ReportEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeFormField
import io.github.sergeyboboshko.composeentity_ksp.base.CeObjectGenerator
import io.github.sergeyboboshko.composeentity_ksp.base.CeReport
import io.github.sergeyboboshko.composeentity_ksp.base.FormFieldCE
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.MigrationEntityCE
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import io.github.sergeyboboshko.composeentity_ksp.entity.GenerationLevel


//створюємо єнтіті для зберігання налаштувань фильтра. Фільтр буде включати в себе адресу та розмір залишку
@Entity(tableName = "rep_utilitypayments_free_settings")
@ObjectGeneratorCE(type = GeneratorType.ReportCursor,label="Free Grouping Balance/Overpayment", generationLevel = GenerationLevel.UI, hasDetails = true, detailsEntityClass = FreeReportUtilityPaymentsEntityResult::class)
@MigrationEntityCE(13)
@CeReport(resultEntity = FreeReportUtilityPaymentsEntityResult::class,
    query = """SELECT * FROM
        (SELECT addressId, utilityId,
        ref_addresses.name AS Address,
        ref_utilities.name AS Utility,
        SUM(CASE WHEN transactionType='EXPENSE' THEN -amount ELSE amount END) AS amount
        FROM areg_payments
        LEFT JOIN ref_addresses
        ON areg_payments.addressId = ref_addresses.id
        LEFT JOIN ref_utilities WHERE areg_payments.utilityId = ref_utilities.id 
        GROUP BY 
        addressId,
        utilityId ) as tab WHERE true 
    """,
    groups = """ 
        addressId,
        utilityId """)
//@MigrationEntityCE (10)
data class ReportUtilityPaymentsFreeEntity(
    @PrimaryKey(autoGenerate = true) override var id: Long,
    override var name:String,
    @FormFieldCE(type = FieldTypeHelper.TEXT,label="@@describe_label", placeHolder = "@@describe_placeholder")
    override var describe:String,
    @FormFieldCE(related = true,label="@@address_label", placeHolder = "@@address_placeholder", relatedEntityClass = RefAddressesEntity::class
        , type = FieldTypeHelper.SELECT, wrapInFilter = true)
    var addressId:Long,
    @FormFieldCE(type = FieldTypeHelper.DECIMAL,label="@@amount_label", placeHolder = "@@amount_placeholder", wrapInFilter = true)
    var amount:Double,
    @FormFieldCE(type = FieldTypeHelper.TEXT, label = "Conditions", placeHolder = "", renderInAddEdit = true)
    override var conditions: String

): ReportEntity(id,"addressId","amount",name,describe)

