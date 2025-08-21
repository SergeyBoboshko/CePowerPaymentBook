package io.github.sergeyboboshko.ceppb.accumulationregisters

import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity

import io.github.sergeyboboshko.ceppb.references.RefAddressesEntity
import io.github.sergeyboboshko.ceppb.references.RefMeterZones
import io.github.sergeyboboshko.ceppb.references.RefMeters
import io.github.sergeyboboshko.ceppb.references.RefUtilitiseEntity
import io.github.sergeyboboshko.composeentity.accumulationregisters.base.CommonAccumRegisterEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons.TransactionType
import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator


@CeEntity(tableName="areg_payments")
@CeCreateTable("areg_payments")
@CeGenerator(type = GeneratorType.AccumulationRegister, label = "Payment Balance")
////@CeMigrationEntity(8)
data class ARegPayments(
    
    override var id: Long,
    override var period: Long,
    override var registratorID: Long,
    override var stringID: Long,
    override var registratorType: Int,
    override var transactionType: TransactionType,
    //---
    @CeField(related = true, type = FieldTypeHelper.SELECT, relatedEntityClass = RefAddressesEntity::class,
        label = "@@address_label", placeHolder = "@@address_placeholder")
    var addressId:Long,
    @CeField(related = true, relatedEntityClass = RefUtilitiseEntity::class, extName = "utility", type = FieldTypeHelper.SELECT
        , label = "@@utility_label", placeHolder = "@@utility_placeholder",  useForOrder = true)
    var utilityId:Long,
    @CeField(related = true, relatedEntityClass = RefMeters::class,
        extName = "meter", type = FieldTypeHelper.SELECT
        , label = "@@meter_label", placeHolder = "@@meter_placeholder",
         useForOrder = true)
    var meterId:Long,
    @CeField(
        related = true,
        relatedEntityClass = RefMeterZones::class,
        extName = "zone",
        type = FieldTypeHelper.SELECT,
        label = "@@zone_label",
        placeHolder = "@@zone_placeholder",
        positionOnForm = 1,
        useForOrder = true
    )
    var zoneId:Long,
    @CeField(label = "@@amount_label", placeHolder = "@@amount_placeholder",type= FieldTypeHelper.DECIMAL)
    var amount: Double,
    @CeField(label = "@@meter_reading_label", placeHolder = "@@meter_reading_placeholder",type= FieldTypeHelper.DECIMAL)
    var meterR:Double
): CommonAccumRegisterEntity(id, period,
    registratorID,
    stringID, registratorType, transactionType)