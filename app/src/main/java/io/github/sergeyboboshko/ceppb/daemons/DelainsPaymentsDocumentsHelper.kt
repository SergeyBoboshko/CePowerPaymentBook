package io.github.sergeyboboshko.ceppb.daemons

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewModelScope

import io.github.sergeyboboshko.ceppb.references.RefUtilitiseEntity
import io.github.sergeyboboshko.composeentity.daemons.BaseUI
import io.github.sergeyboboshko.composeentity.daemons.FieldValidator
import io.github.sergeyboboshko.composeentity.daemons.FormType
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsExtEntity
import io.github.sergeyboboshko.composeentity.documents.base.CommonDocumentExtEntity
import io.github.sergeyboboshko.composeentity_ksp.AppGlobalCE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
//interfase for froupe of documents
interface DocsPayment{
    var addressId:Long
    var date:Long
}
interface DetailsPaymentHelperClass{
    var parentId: Long
    var meterId:Long
    var utilityId:Long
}

//io.github.sergeyboboshko.ceppb.daemons.DetailsPaymentDocumentsHelper
object DetailsPaymentDocumentsHelper {
    var lastMeterReadings:Float = 0f
    fun meterReadingCondition(): FieldValidator {
        return object : FieldValidator {
            override var errorMessage = "Meter reading must be equals or biggest then previous one"
            override fun isValid(value: Any):Boolean{
                val a= value.toString().toFloatOrNull()?:0f
                return a>=lastMeterReadings
            }
        }
    }

    fun onUtilityEdited(currentValue: Any, vm: _BaseFormVM, ui: BaseUI, docVM: _BaseFormVM) {
        val current = docVM.anyItem as CommonDocumentExtEntity // take addressId from current document rendering on screen
        val addrID = (current.link as DocsPayment).addressId
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
    fun LastReading(vm: _BaseFormVM, formType: FormType? = null, docVM: _BaseFormVM) {
        val viewCounter = vm.viewCounter.value
        val currElementExt = vm.anyItem as? CommonDetailsExtEntity
        val currElement:DetailsPaymentHelperClass? = (currElementExt?.link as? DetailsPaymentHelperClass)
        var parentId = currElement?.parentId
        val lastReading = remember { mutableStateOf<Float?>(null) }
       //if(parent == null){
           val parent = docVM.anyItem?.link as DocsPayment
       //}
        if (parent == null) {
            Text("No previous readings")
            return
        }
        // Function to update the value
        fun updateReading(utilityId: Long?, meterId: Long?, period: Long?) {
            val query =
                "SELECT MAX(meterR) as lastReading FROM areg_payments WHERE addressId=? AND utilityId=? AND meterId=? and period < ?"
            val params = arrayOf(parent.addressId, utilityId ?: 0L, meterId ?: 0L, period?:0L)

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
            var period = parent.date

            if (utility.equals("")) utility = "0"
            if (meter.equals("")) meter = "0"
            //if (period.equals("")) period = "0"

            //LaunchedEffect(utility, meter) {
            LaunchedEffect(viewCounter) {
                val utilityId = utility?.toLongOrNull()
                val meterId = meter?.toLongOrNull()
                val period =  period//.toLongOrNull()
                updateReading(utilityId, meterId,period)
            }
        } else {
            LaunchedEffect(currElement?.meterId) {
                val utilityId = currElement?.utilityId
                val meterId = currElement?.meterId
                val period = currElement?.parentId
                updateReading(utilityId, meterId,period)
            }
        }

        if (lastReading.value != null) {
            Text("Last reading: ${lastReading.value}")
        } else {
            Text("No previous readings 2")
        }
    }
}