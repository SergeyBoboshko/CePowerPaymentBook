package io.github.sergeyboboshko.cereport.documents

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.cereport.details.DetailsUtilityCharge
import io.github.sergeyboboshko.cereport.details.DetailsUtilityPayment
import io.github.sergeyboboshko.cereport.references.RefAddressesEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.documents.base.CommonDocumentEntity
import io.github.sergeyboboshko.composeentity_ksp.base.FormFieldCE
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.MigrationEntityCE
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "doc_utility_payment")
@ObjectGeneratorCE(type = GeneratorType.Document, label = "Document Utility Payment",
    hasDetails = true, detailsEntityClass = DetailsUtilityPayment::class)
@MigrationEntityCE(migrationVersion = 4)
data class DocUtilityPayment(
    @PrimaryKey(autoGenerate = true)
    override var id: Long,
    override var date: Long,
    override var number: Long,
    override var isPosted: Boolean,
    override var isMarkedForDeletion: Boolean,
    //Address, Describe
    @FormFieldCE(related = true, type = FieldTypeHelper.SELECT, relatedEntityClass = RefAddressesEntity::class,
        label = "@@address_label", placeHolder = "@@address_placeholder")
    var addressId:Long,
    @FormFieldCE(label = "@@describe_label", placeHolder = "@@describe_placeholder",type= FieldTypeHelper.TEXT)
    var describe:String
): CommonDocumentEntity(
    id,date, number, isPosted , isMarkedForDeletion
), Parcelable