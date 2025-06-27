package io.github.sergeyboboshko.cereport.details

import android.os.Parcelable
import androidx.room.AutoMigration
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec
import androidx.sqlite.db.SupportSQLiteDatabase
import io.github.sergeyboboshko.cereport.documents.DocSubsidy
import io.github.sergeyboboshko.cereport.documents.DocUtilityCharge
import io.github.sergeyboboshko.cereport.documents.DocUtilityPayment
import io.github.sergeyboboshko.cereport.references.RefMeters
import io.github.sergeyboboshko.cereport.references.RefUtilitiseEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsEntity
import io.github.sergeyboboshko.composeentity_ksp.base.FormFieldCE
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.MigrationEntityCE
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import kotlinx.android.parcel.Parcelize

@ObjectGeneratorCE(type = GeneratorType.Details, label = "The Details Subsidy")
@Parcelize
@Entity(
    tableName = "details_subsidy",
    foreignKeys = [
        ForeignKey(
            entity = DocSubsidy::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

class DetailsSubsidy(
    @PrimaryKey(autoGenerate = true)
    override var id: Long,
    override var parentId: Long,
    @FormFieldCE(
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
    @FormFieldCE(
        related = true,
        relatedEntityClass = RefMeters::class,
        extName = "meter",
        type = FieldTypeHelper.SELECT,
        label = "@@meter_label",
        placeHolder = "@@meter_placeholder",
        positionOnForm = 1,
        useForOrder = true
    )
    var meterId: Long,
    @FormFieldCE(
        label = "@@amount_label",
        placeHolder = "@@amount_placeholder",
        type = FieldTypeHelper.DECIMAL
    )
    var amount: Double,
    @FormFieldCE(
        label = "@@describe_label",
        placeHolder = "@@describe_placeholder",
        type = FieldTypeHelper.TEXT
    )
    var describe: String,

    @FormFieldCE(
        label = "@@meter_reading_label",
        placeHolder = "@@meter_reading_placeholder",
        type = FieldTypeHelper.DECIMAL
    )
    var meterR: Double
) : CommonDetailsEntity(id, parentId), Parcelable {

}

