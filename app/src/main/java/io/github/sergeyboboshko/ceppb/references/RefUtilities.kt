package io.github.sergeyboboshko.ceppb.references

import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity
import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


import io.github.sergeyboboshko.ceppb.MyApplication1
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons.FormType
import io.github.sergeyboboshko.composeentity.daemons.SaveOperationTypes
import io.github.sergeyboboshko.composeentity.daemons.SimpleDataPickerDialog
import io.github.sergeyboboshko.composeentity.daemons.WebPageViewer
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentity_ksp.base.CeIgnore

@CeGenerator(
    type = GeneratorType.Reference,
    label = "The Utilities",
    beforeSave = "RefUtilitiesHelper.beforeSave",
    beforeDelete = "RefUtilitiesHelper.beforeDeleteUtility"
)

@CeEntity(tableName = "ref_utilities")
@CeCreateTable("ref_utilities")
class RefUtilitiseEntity(
    
    override var id: Long,
    override var date: Long,
    @CeField(
        label = "@@name_label",
        placeHolder = "@@name_placeholder",
        type = FieldTypeHelper.TEXT,
        positionOnForm = 1,
        useForOrder = true
    )
    override var name: String,
    override var isMarkedForDeletion: Boolean,
    @CeField(
        label = "@@address_label",
        placeHolder = "@@address_placeholder",
        type = FieldTypeHelper.TEXT,
        positionOnForm = 5,
        useForOrder = true
    )
    var physicalAddress: String,
    @CeField(
        label = "@@email_label",
        placeHolder = "@@email_placeholder",
        type = FieldTypeHelper.TEXT,
        positionOnForm = 10,
        useForOrder = true
    )
    var emailAddress: String,
    @CeField(
        label = "@@phone_label",
        placeHolder = "@@phone_placeholder",
        type = FieldTypeHelper.TEXT,
        positionOnForm = 15,
        useForOrder = true
    )
    var phoneNumber: String,
    @CeField(
        label = "@@personal_account_label",
        placeHolder = "@@personal_account_placeholder",
        type = FieldTypeHelper.TEXT,
        positionOnForm = 20,
        useForOrder = true
    )
    var p_account: String,
    @CeField(
        label = "@@describe_label",
        placeHolder = "@@describe_placeholder",
        type = FieldTypeHelper.TEXT,
        positionOnForm = 25
    )
    var describe: String
) : CommonReferenceEntity(id, date, name, isMarkedForDeletion){
    override fun toString(): String {
        return "$id: $name"
    }

    
    @CeField(
        label = "Web Page",
        type = FieldTypeHelper.COMPOSABLE,
        customComposable = "RefUtilitiesHelper.ShowWebPage",
        renderInView = true, renderInList = false, renderInAddEdit = false

    )
    @CeIgnore
    var webp: String = ""
}

object RefUtilitiesHelper {
    @Composable
    fun ShowWebPage(vm: _BaseFormVM, formType: FormType? = null) {
        val entity = vm.anyItem as RefUtilitiseEntityExt
        val describe = entity.link.describe
        if (describe != null && describe.startsWith("http")) {
            WebPageViewer(describe)
        } else {
            Text("If You put the link in the Describe field, you will see the web page")
        }
    }

    fun beforeSave(
        sot: SaveOperationTypes,
        viewModel: RefUtilitiseEntityViewModel,
        ui: RefUtilitiseEntityUI
    ) {
        if (sot == SaveOperationTypes.SAVE) {
            if (viewModel.getField("describe").toString().isBlank()) {
                viewModel.updateField(
                    "describe",
                    "Saved " + SimpleDataPickerDialog(0).convertMillisToDate(System.currentTimeMillis())
                )
            }
        } else {
            Toast.makeText(MyApplication1.appContext, "Another operation", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun beforeDeleteUtility(
        delList: List<Any>?,
        viewModel: RefUtilitiseEntityViewModel,
        ui: RefUtilitiseEntityUI
    ): Boolean {
        for (item in delList ?: emptyList()) {
            val curr = item as RefUtilitiseEntityExt //RefUtilitiesEntity + Ext
            if (curr.link.describe.isNotBlank() && !curr.link.isMarkedForDeletion) {
                Toast.makeText(
                    MyApplication1.appContext,
                    "Can't delete course of describe is not empty but [${curr.link.describe}]",
                    Toast.LENGTH_SHORT
                ).show()
                return true
            }
        }
        return false
    }
}