package io.github.sergeyboboshko.cereport.accumulationregisters

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.cereport.references.RefAddressesEntity
import io.github.sergeyboboshko.cereport.references.RefMeters
import io.github.sergeyboboshko.cereport.references.RefUtilitiseEntity
import io.github.sergeyboboshko.composeentity.accumulationregisters.base.CommonAccumRegisterEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons.TransactionType
import io.github.sergeyboboshko.composeentity_ksp.base.FormFieldCE
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.MigrationEntityCE
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName="areg_payments")
@ObjectGeneratorCE(type = GeneratorType.AccumulationRegister, label = "Payment Balance")
@MigrationEntityCE(8)
data class ARegPayments(
    @PrimaryKey(autoGenerate = true)
    override var id: Long,
    override var period: Long,
    override var registratorID: Long,
    override var stringID: Long,
    override var registratorType: Int,
    override var transactionType: TransactionType,
    //---
    @FormFieldCE(related = true, type = FieldTypeHelper.SELECT, relatedEntityClass = RefAddressesEntity::class,
        label = "@@address_label", placeHolder = "@@address_placeholder")
    var addressId:Long,
    @FormFieldCE(related = true, relatedEntityClass = RefUtilitiseEntity::class, extName = "utility", type = FieldTypeHelper.SELECT
        , label = "@@utility_label", placeHolder = "@@utility_placeholder",  useForOrder = true)
    var utilityId:Long,
    @FormFieldCE(related = true, relatedEntityClass = RefMeters::class,
        extName = "meter", type = FieldTypeHelper.SELECT
        , label = "@@meter_label", placeHolder = "@@meter_placeholder",
         useForOrder = true)
    var meterId:Long,
    @FormFieldCE(label = "@@amount_label", placeHolder = "@@amount_placeholder",type= FieldTypeHelper.DECIMAL)
    var amount: Double,
    @FormFieldCE(label = "@@meter_reading_label", placeHolder = "@@meter_reading_placeholder",type= FieldTypeHelper.DECIMAL)
    var meterR:Double
): CommonAccumRegisterEntity(id, period,
    registratorID,
    stringID, registratorType, transactionType), Parcelable