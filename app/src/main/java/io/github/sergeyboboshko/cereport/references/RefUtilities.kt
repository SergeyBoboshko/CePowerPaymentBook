package io.github.sergeyboboshko.cereport.references

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceEntity
import io.github.sergeyboboshko.composeentity_ksp.base.FormFieldCE
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.MigrationEntityCE
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import kotlinx.android.parcel.Parcelize

@ObjectGeneratorCE(type = GeneratorType.Reference, label = "The Utilities")
@Parcelize
@Entity(tableName="ref_utilities")
@MigrationEntityCE(2)
class RefUtilitiseEntity(
    @PrimaryKey(autoGenerate = true)
    override var id: Long,
    override var date: Long,
    @FormFieldCE(label = "@@name_label", placeHolder = "@@name_placeholder",type= FieldTypeHelper.TEXT, positionOnForm = 1, useForOrder = true)
    override var name: String,
    override var isMarkedForDeletion: Boolean,
    @FormFieldCE(label = "@@address_label", placeHolder = "@@address_placeholder",type= FieldTypeHelper.TEXT, positionOnForm = 5, useForOrder = true)
    var physicalAddress:String,
    @FormFieldCE(label = "@@email_label", placeHolder = "@@email_placeholder",type= FieldTypeHelper.TEXT, positionOnForm = 10, useForOrder = true)
    var emailAddress:String,
    @FormFieldCE(label = "@@phone_label", placeHolder = "@@phone_placeholder",type= FieldTypeHelper.TEXT, positionOnForm = 15, useForOrder = true)
    var phoneNumber:String,
    @FormFieldCE(label = "@@personal_account_label", placeHolder = "@@personal_account_placeholder",type= FieldTypeHelper.TEXT, positionOnForm = 20, useForOrder = true)
    var p_account:String,
    @FormFieldCE(label = "@@describe_label", placeHolder = "@@describe_placeholder",type= FieldTypeHelper.TEXT, positionOnForm = 25)
    var describe:String
    ): CommonReferenceEntity(id,date,name,isMarkedForDeletion), Parcelable {
    override fun toString(): String {
        return "$id: $name"
    }
}