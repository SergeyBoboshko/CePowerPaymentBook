package io.github.sergeyboboshko.ceppb.details

import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity



import io.github.sergeyboboshko.ceppb.references.RefAddressesEntity
import io.github.sergeyboboshko.ceppb.references.RefMeters
import io.github.sergeyboboshko.ceppb.references.RefUtilitiseEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator

@CeGenerator(type = GeneratorType.Details, label = "The Utilities of Address")
@CeEntity(tableName = "ref_adress_details")
@CeCreateTable("ref_adress_details")
////@CeMigrationEntity(migrationVersion = 1)
class DetailsAddressEntity(
    
    override var id: Long,
    override var parentId: Long,
    @CeField(related = true, relatedEntityClass = RefUtilitiseEntity::class, extName = "utility", type = FieldTypeHelper.SELECT
    , label = "@@utility_label", placeHolder = "@@utility_placeholder", positionOnForm = 1, useForOrder = true)
    var utilityId:Long,
    
    @CeField(related = true, relatedEntityClass = RefMeters::class, extName = "meter", type = FieldTypeHelper.SELECT
        , label = "@@meter_label", placeHolder = "@@meter_placeholder", positionOnForm = 3, useForOrder = true)
    var meterId:Long,
    @CeField(label = "@@describe_label", placeHolder = "@@describe_placeholder",type= FieldTypeHelper.TEXT, positionOnForm = 5)
    var describe:String
):CommonDetailsEntity(id,parentId) {

}