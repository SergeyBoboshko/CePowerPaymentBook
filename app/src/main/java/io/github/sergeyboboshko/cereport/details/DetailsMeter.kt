package io.github.sergeyboboshko.cereport.details

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.cereport.references.RefAddressesEntity
import io.github.sergeyboboshko.cereport.references.RefMeterZones
import io.github.sergeyboboshko.cereport.references.RefMeters
import io.github.sergeyboboshko.cereport.references.RefUtilitiseEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsEntity
import io.github.sergeyboboshko.composeentity_ksp.base.FormFieldCE
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.MigrationEntityCE
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import kotlinx.android.parcel.Parcelize

@ObjectGeneratorCE(type = GeneratorType.Details, label = "The Zones")
@Parcelize
@Entity(tableName = "ref_meter_details",
    foreignKeys = [
        ForeignKey(
            entity = RefMeters::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
@MigrationEntityCE(migrationVersion = 1)
class DetailsMeterEntity(
    @PrimaryKey(autoGenerate = true)
    override var id: Long,
    override var parentId: Long,
    @FormFieldCE(related = true, relatedEntityClass = RefMeterZones::class, extName = "zone", type = FieldTypeHelper.SELECT
        , label = "@@zone_label", placeHolder = "@@zone_placeholder", positionOnForm = 5, useForOrder = true)
    var zoneId:Long,
    @FormFieldCE(label = "@@describe_label", placeHolder = "@@describe_placeholder",type= FieldTypeHelper.TEXT, positionOnForm = 10)
    var describe:String
): CommonDetailsEntity(id,parentId), Parcelable {

}