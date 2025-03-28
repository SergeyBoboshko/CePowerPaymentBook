package io.github.sergeyboboshko.cereport.references

import android.os.Parcelable
import android.widget.Toast
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.cereport.MyApplication1
import io.github.sergeyboboshko.cereport.details.DetailsAddressEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons.FieldValidator
import io.github.sergeyboboshko.composeentity.daemons.SaveOperationTypes
import io.github.sergeyboboshko.composeentity.daemons.SimpleDataPickerDialog
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceEntity
import io.github.sergeyboboshko.composeentity_ksp.base.FormFieldCE
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.MigrationEntityCE
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import io.github.sergeyboboshko.composeentity_ksp.entity.GenerationLevel

import kotlinx.android.parcel.Parcelize

@ObjectGeneratorCE(
    type = GeneratorType.Reference, label = "Addresses", generationLevel = GenerationLevel.UI,
    hasDetails = true, detailsEntityClass = DetailsAddressEntity::class
)
@Parcelize
@Entity(tableName = "ref_addresses")
@MigrationEntityCE(1)
class RefAddressesEntity(
    @PrimaryKey(autoGenerate = true)
    override var id: Long,
    override var date: Long,
    @FormFieldCE(
        label = "@@name_label", placeHolder = "@@name_placeholder", type = FieldTypeHelper.TEXT,
        positionOnForm = 1, useForOrder = true, onEndEditing = "AddressHelper.onNameChanged"
        , condition = "AddressHelper.nameValidator"
    )
    override var name: String,
    override var isMarkedForDeletion: Boolean,
    @FormFieldCE(
        label = "@@zipCode_label",
        placeHolder = "@@zipCode_placeholder",
        type = FieldTypeHelper.TEXT,
        positionOnForm = 5,
        useForOrder = true,
        renderInList = false,
        renderInView = false,
        condition = "AddressHelper.zipValidator"
    )
    var zipCode: String,
    @FormFieldCE(
        label = "@@city_label",
        placeHolder = "@@city_placeholder",
        type = FieldTypeHelper.TEXT,
        positionOnForm = 10,
        useForOrder = true
    )
    var city: String,
    @FormFieldCE(
        label = "@@address_label",
        placeHolder = "@@address_placeholder",
        type = FieldTypeHelper.TEXT,
        positionOnForm = 15,
        useForOrder = true
    )
    var address: String,
    @FormFieldCE(
        label = "@@houseNumber_label",
        placeHolder = "@@houseNumber_placeholder",
        type = FieldTypeHelper.NUMBER,
        positionOnForm = 15,
        useForOrder = true,
        renderInList = false
    )
    var house: Int,
    @FormFieldCE(
        label = "@@houseBlock_label",
        placeHolder = "@@houseBlock_placeholder",
        type = FieldTypeHelper.TEXT,
        positionOnForm = 20,
        renderInList = false
    )
    var houseBlock: String,
    @FormFieldCE(
        label = "@@apartmentNumber_label",
        placeHolder = "@@apartmentNumber_placeholder",
        type = FieldTypeHelper.NUMBER,
        positionOnForm = 25,
        renderInList = false
    )
    var apartment: Int
) : CommonReferenceEntity(id, date, name, isMarkedForDeletion), Parcelable {
    override fun toString(): String {
        return "$id: $name"
    }
}

object AddressHelper {



    fun onNameChanged(currentValue: Any, vm: _BaseFormVM, ui: RefAddressesEntityUI) {
        val address = vm._formData["address"]
        if (address != null) {
            if (address.isBlank()) {
                vm._formData["address"] = currentValue as String
                vm.updateView()
            }
        }
    }

    fun nameValidator(): FieldValidator {
        return object : FieldValidator {
            override var errorMessage = "Name must have 2 or more symbols"
            override fun isValid(value: Any)=value.toString().length>1
        }
    }

    fun zipValidator(): FieldValidator {
        return object : FieldValidator {
            override var errorMessage = "Zip code must be 5 digits"
            override fun isValid(value: Any):Boolean{
                if(value.toString().length==5){
                    return true
                }
                else{
                    val len = value.toString().length
                    errorMessage = "Zip code must be 5 digits but find $len"
                    return false
                }
            }
        }
    }
}