package io.github.sergeyboboshko.cereport.references

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceEntity
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeMigrationEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import kotlinx.android.parcel.Parcelize

@CeGenerator(type = GeneratorType.Reference
    , label = "The Types of Meters")
@Parcelize
@Entity(tableName="ref_typesofmeters")
//@CeMigrationEntity(1)
data class RefTypesOfMeters (
    @PrimaryKey(autoGenerate = true)
    override var id: Long,
    override var date: Long,
    override var name: String,
    override var isMarkedForDeletion: Boolean
): CommonReferenceEntity(id,date,name,isMarkedForDeletion), Parcelable{
    override fun toString(): String {
        return "$id: $name"
    }
}