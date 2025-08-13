package io.github.sergeyboboshko.ceppb.details

import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity
import io.github.sergeyboboshko.ceppb.documents.DocTariffRegistry
import io.github.sergeyboboshko.ceppb.references.RefMeterZones
import io.github.sergeyboboshko.ceppb.references.RefUtilitiseEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType


@CeGenerator(type = GeneratorType.Details, label = "@@details_tariff_registry")
@CeEntity(
    tableName = "details_tariff_registry")
@CeCreateTable("details_tariff_registry")
data class DetailsTariffRegistry(
    
    override var id: Long,
    override var parentId: Long,
    @CeField(
        related = true,
        relatedEntityClass = RefUtilitiseEntity::class,
        extName = "utility",
        type = FieldTypeHelper.SELECT,
        label = "@@utility_label",
        placeHolder = "@@utility_placeholder",
        positionOnForm = 1,
        useForOrder = true
    )
    var utilityId: Long,
    
    @CeField(related = true, relatedEntityClass = RefMeterZones::class, extName = "zone", type = FieldTypeHelper.SELECT
        , label = "@@zone_label", placeHolder = "@@zone_placeholder", positionOnForm = 5, useForOrder = true)
    var zoneId:Long,
    @CeField(label = "@@amount_label", placeHolder = "@@amount_placeholder", type = FieldTypeHelper.DECIMAL)
    var amount: Double,
    @CeField(label = "@@describe_label", placeHolder = "@@describe_placeholder", type = FieldTypeHelper.TEXT)
    var describe: String
): CommonDetailsEntity(id, parentId)