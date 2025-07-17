package io.github.sergeyboboshko.cereport.details

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.cereport.references.RefAddressesEntity
import io.github.sergeyboboshko.cereport.references.RefMeters
import io.github.sergeyboboshko.cereport.references.RefUtilitiseEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeMigrationEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import kotlinx.android.parcel.Parcelize

@CeGenerator(type = GeneratorType.Details, label = "The Utilities of Address")
@Parcelize
@Entity(tableName = "ref_adress_details",
    foreignKeys = [
        ForeignKey(
            entity = RefAddressesEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
//@CeMigrationEntity(migrationVersion = 1)
class DetailsAddressEntity(
    @PrimaryKey(autoGenerate = true)
    override var id: Long,
    override var parentId: Long,
    @CeField(related = true, relatedEntityClass = RefUtilitiseEntity::class, extName = "utility", type = FieldTypeHelper.SELECT
    , label = "@@utility_label", placeHolder = "@@utility_placeholder", positionOnForm = 1, useForOrder = true)
    var utilityId:Long,
    @ColumnInfo(defaultValue = "0")
    @CeField(related = true, relatedEntityClass = RefMeters::class, extName = "meter", type = FieldTypeHelper.SELECT
        , label = "@@meter_label", placeHolder = "@@meter_placeholder", positionOnForm = 3, useForOrder = true)
    var meterId:Long,
    @CeField(label = "@@describe_label", placeHolder = "@@describe_placeholder",type= FieldTypeHelper.TEXT, positionOnForm = 5)
    var describe:String
):CommonDetailsEntity(id,parentId),Parcelable {

}