package io.github.sergeyboboshko.ceppb.details

import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity


import io.github.sergeyboboshko.ceppb.references.RefMeterZones
import io.github.sergeyboboshko.ceppb.references.RefMeters
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator

@CeGenerator(type = GeneratorType.Details, label = "The Zones")

@CeEntity(tableName = "ref_meter_details")
@CeCreateTable("ref_meter_details")
////@CeMigrationEntity(migrationVersion = 1)
class DetailsMeterEntity(
    
    override var id: Long,
    override var parentId: Long,
    @CeField(related = true, relatedEntityClass = RefMeterZones::class, extName = "zone", type = FieldTypeHelper.SELECT
        , label = "@@zone_label", placeHolder = "@@zone_placeholder", positionOnForm = 5, useForOrder = true)
    var zoneId:Long,
    @CeField(label = "@@describe_label", placeHolder = "@@describe_placeholder",type= FieldTypeHelper.TEXT, positionOnForm = 10)
    var describe:String
): CommonDetailsEntity(id,parentId){

}