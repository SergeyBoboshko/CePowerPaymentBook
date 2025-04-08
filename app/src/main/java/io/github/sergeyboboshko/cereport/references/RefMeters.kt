package io.github.sergeyboboshko.cereport.references

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.cereport.details.DetailsMeterEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceEntity
import io.github.sergeyboboshko.composeentity_ksp.base.FormFieldCE
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.MigrationEntityCE
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import kotlinx.android.parcel.Parcelize

@ObjectGeneratorCE(type = GeneratorType.Reference
    , label = "The Meters", hasDetails = true, detailsEntityClass = DetailsMeterEntity::class
)
@Parcelize
@Entity(tableName="ref_meters")
//@MigrationEntityCE(1)
data class RefMeters(
    @PrimaryKey(autoGenerate = true)
    override var id: Long,
    override var date: Long,
    override var name: String,
    override var isMarkedForDeletion: Boolean,

    @FormFieldCE(related = true, type = FieldTypeHelper.SELECT, label = "Meter's type", placeHolder = "Select type of the meter"
        , relatedEntityClass = RefTypesOfMeters::class, extName = "type")
    var type:Long
): CommonReferenceEntity(id,date,name,isMarkedForDeletion), Parcelable{
    override fun toString(): String {
        return "$id: $name"
    }
}