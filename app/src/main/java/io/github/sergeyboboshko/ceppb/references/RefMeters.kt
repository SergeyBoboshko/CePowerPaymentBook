package io.github.sergeyboboshko.ceppb.references

import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity

import io.github.sergeyboboshko.ceppb.details.DetailsMeterEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator

@CeGenerator(type = GeneratorType.Reference
    , label = "The Meters", hasDetails = true, detailsEntityClass = DetailsMeterEntity::class
)

@CeEntity(tableName="ref_meters")
@CeCreateTable("ref_meters")
data class RefMeters(
    
    override var id: Long,
    override var date: Long,
    override var name: String,
    override var isMarkedForDeletion: Boolean,

    @CeField(related = true, type = FieldTypeHelper.SELECT, label = "Meter's type", placeHolder = "Select type of the meter"
        , relatedEntityClass = RefTypesOfMeters::class, extName = "type")
    var type:Long
): CommonReferenceEntity(id,date,name,isMarkedForDeletion){
    override fun toString(): String {
        return "$id: $name"
    }
}