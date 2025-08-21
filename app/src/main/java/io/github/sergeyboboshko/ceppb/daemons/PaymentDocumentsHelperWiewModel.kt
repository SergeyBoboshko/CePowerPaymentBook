package io.github.sergeyboboshko.ceppb.daemons

import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.sergeyboboshko.composeentity.daemons.FormType
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.documents.base.CommonDocumentEntity
import io.github.sergeyboboshko.composeentity.documents.base.CommonDocumentExtEntity
import io.github.sergeyboboshko.composeentity_ksp.AppGlobalCE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaymentDocumentsHelperWiewModel : ViewModel() {
    val _refresher = MutableStateFlow(true)
    val refresher = _refresher.asStateFlow()


    fun fillDetails(
        detailsTableName: String,
        currentDoc: CommonDocumentExtEntity<CommonDocumentEntity>
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                //Toast.makeText(MyApplication1.appContext,"We Filling Details",Toast.LENGTH_SHORT).show()
                val sqlDelete = "DELETE FROM $detailsTableName WHERE parentId = ?"
                val sqlInsert = """
                         INSERT INTO $detailsTableName (
                                parentId, utilityId, meterId,  zoneId, amount, describe, meterR
                            )
                            SELECT ? as parentDocId,
                             mainTab.utilityId as utilityId,
                             mainTab.meterId as meterId,
                             IFNULL(ref_meter_details.zoneId,0) as zoneId,
                             IFNULL(tab1.amount,0)  as amount,
                             mainTab.describe as describe, 
                             0.0 as meterR
                            FROM ref_adress_details as mainTab
                            LEFT JOIN ref_meter_details as ref_meter_details
                            ON mainTab.meterId = ref_meter_details.parentId
                            LEFT JOIN (
                                SELECT 
                                datesTable.period as period,
                                datesTable.utilityId as utilityId,
                                datesTable.zoneId as zoneId,
                                IFNULL(inforeg_tariffs.amount,0) as amount
                                FROM (SELECT
                                    MAX(period) as period,
                                    utilityId,
                                    zoneId
                                    FROM inforeg_tariffs
                                    WHERE period <= ?
                                    GROUP BY utilityId,
                                    zoneId
                                    ) as datesTable
                                    LEFT JOIN
                                    inforeg_tariffs
                                    ON datesTable.period = inforeg_tariffs.period AND
                                    datesTable.utilityId = inforeg_tariffs.utilityId AND
                                    datesTable.zoneId = inforeg_tariffs.zoneId
                                    
                                 ) as tab1
                                 ON mainTab.utilityId = tab1.utilityId AND
                                   IFNULL(ref_meter_details.zoneId,0) = IFNULL(tab1.zoneId,0)
                         WHERE mainTab.parentId = ?

                        """.trimIndent()


                AppGlobalCE.forSQLViewModel.execSQL(
                    sqlDelete,
                    arrayOf(currentDoc.link.id)
                )
                AppGlobalCE.forSQLViewModel.execSQL(
                    sqlInsert,
                    arrayOf(
                        currentDoc.link.id, currentDoc.link.date,
                        (currentDoc.link as DocsPayment).addressId
                    )
                )
                _refresher.value = !_refresher.value
            }
        }
    }

    val _lastMeterReadings = MutableStateFlow(0f)
    val lastMeterReadings = _lastMeterReadings.asStateFlow()

    val _amountCount = MutableStateFlow(0f)
    val amountCount = _amountCount.asStateFlow()

    fun gefAmountCount(period:Long,meterReading: Float, vm: _BaseFormVM, formType: FormType? = null) {
        val meterReadingDifferent = meterReading - lastMeterReadings.value
        if (meterReadingDifferent > 0) {
            if (formType == null) {
                viewModelScope.launch {
                    val utilityId = vm.getField("utilityId").toString().toLong()
                    val meterId = vm.getField("meterId").toString().toLong()
                    if (meterId > 0) {
                        val zoneId = vm.getField("zoneId").toString().toLong()
                        getTariffAmount(period,utilityId, zoneId, meterReadingDifferent, vm)
                    }
                }
            }
        }
    }

    suspend fun getTariffAmount(
        period:Long,
        utilityId: Long,
        zoneId: Long,
        meterReadingDifferent: Float,
        vm: _BaseFormVM
    ) {
        //get tariff
        withContext(Dispatchers.IO) {
            val sqlText = """
           SELECT 
            datesTable.period as period,
            datesTable.utilityId as utilityId,
            datesTable.zoneId as zoneId,
            IFNULL(inforeg_tariffs.amount,0) as amount
            FROM (SELECT
                MAX(period) as period,
                 utilityId,
                 zoneId
                FROM inforeg_tariffs
                 WHERE period <= ? AND utilityId=? AND zoneId=?
                GROUP BY utilityId,
                 zoneId
                 ) as datesTable
               LEFT JOIN
               inforeg_tariffs
                ON datesTable.period = inforeg_tariffs.period AND
                datesTable.utilityId = inforeg_tariffs.utilityId AND
                datesTable.zoneId = inforeg_tariffs.zoneId        
            --WHERE datesTable.period <= ? AND datesTable.utilityId=? AND datesTable.zoneId=? 
          """.trimIndent()
            val res =AppGlobalCE.forSQLViewModel.repository.generateResultFromReport(
                sqlText,
                arrayOf(period,utilityId,zoneId)
            )
            var amount = 0f
            res.use {
                if (it.moveToFirst()) {
                    val idx = it.getColumnIndexOrThrow("amount")
                    amount = it.getFloat(idx)
                    _amountCount.value = amount * meterReadingDifferent
                    vm.updateField("amount",_amountCount.value)
                    vm.updateView()
                }
            }

        }
    }

}