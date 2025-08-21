package io.github.sergeyboboshko.ceppb.informationregisters

import io.github.sergeyboboshko.ceppb.references.RefMeterZones
import io.github.sergeyboboshko.ceppb.references.RefUtilitiseEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons.TransactionType
import io.github.sergeyboboshko.composeentity.informationregisters.base.InfRegEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable
import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType

@CeCreateTable("inforeg_tariffs")
@CeEntity("inforeg_tariffs")
@CeGenerator(type = GeneratorType.InformationRegister, label = "@@info_reg_tariffs_label")
class Tariffs(
    override var id: Long,
    @CeField(type = FieldTypeHelper.DATE_TIME)
    override var period: Long,
    override var registratorID: Long,
    override var stringID: Long,
    override var registratorType: Int,
    override var transactionType: TransactionType,
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
    var amount: Double

):InfRegEntity(id,period,registratorID,stringID,registratorType) {

}