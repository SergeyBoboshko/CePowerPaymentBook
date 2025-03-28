package io.github.sergeyboboshko.cereport.details

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.cereport.documents.DocUtilityCharge
import io.github.sergeyboboshko.cereport.references.RefAddressesEntity
import io.github.sergeyboboshko.cereport.references.RefMeters
import io.github.sergeyboboshko.cereport.references.RefUtilitiseEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsEntity
import io.github.sergeyboboshko.composeentity_ksp.base.FormFieldCE
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.MigrationEntityCE
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import kotlinx.android.parcel.Parcelize
import java.time.temporal.TemporalAmount

@ObjectGeneratorCE(type = GeneratorType.Details, label = "The Details Charge")
@Parcelize
@Entity(tableName = "details_utility_charge",
    foreignKeys = [
        ForeignKey(
            entity = DocUtilityCharge::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
@MigrationEntityCE(migrationVersion = 3)
class DetailsUtilityCharge(
    @PrimaryKey(autoGenerate = true)
    override var id: Long,
    override var parentId: Long,
    @FormFieldCE(related = true, relatedEntityClass = RefUtilitiseEntity::class, extName = "utility", type = FieldTypeHelper.SELECT
        , label = "@@utility_label", placeHolder = "@@utility_placeholder", positionOnForm = 1, useForOrder = true)
    var utilityId:Long,
    @FormFieldCE(related = true, relatedEntityClass = RefMeters::class,
        extName = "meter", type = FieldTypeHelper.SELECT
        , label = "@@meter_label", placeHolder = "@@meter_placeholder",
        positionOnForm = 1, useForOrder = true)
    var meterId:Long,
    @FormFieldCE(label = "@@amount_label", placeHolder = "@@amount_placeholder",type= FieldTypeHelper.DECIMAL)
    var amount: Double,
    @FormFieldCE(label = "@@describe_label", placeHolder = "@@describe_placeholder",type= FieldTypeHelper.TEXT)
    var describe:String
): CommonDetailsEntity(id,parentId), Parcelable {

}