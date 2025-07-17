package io.github.sergeyboboshko.cereport.free

import android.database.Cursor
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import io.github.sergeyboboshko.cereport.R
import io.github.sergeyboboshko.composeentity.daemons.AutoDisplayCursorResult
import io.github.sergeyboboshko.composeentity.daemons.BaseEntity
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.daemons.IconVector
import io.github.sergeyboboshko.composeentity.daemons.ReportField
import io.github.sergeyboboshko.composeentity.daemons.StyledButton
import io.github.sergeyboboshko.composeentity.daemons.TransactionType
import io.github.sergeyboboshko.composeentity.daemons.localization.LocalizationManager
import io.github.sergeyboboshko.composeentity.daemons.openGeneratedPdfFromCursor
import io.github.sergeyboboshko.composeentity_ksp.base.CeFree
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentity_ksp.entity.GenerationLevel

@CeGenerator(type = GeneratorType.Free, label = "Payments Balance", generationLevel = GenerationLevel.UI)
@CeFree(fields = "fields", alternatingRowColors = true,withRowNumber = true,
    onResult = "ShowResult")
data class FreeReportUtilityPaymentsEntityResult (
    override var id: Long,
    var period: Long,
    var registratorID: Long,
    var stringID: Long,
    var registratorType: Int,
    var transactionType: TransactionType,
    var addressId:Long,
    var zoneId: Long,
    var amount: Double
): BaseEntity {
    @Composable
    override fun StatusIcon() {
        Icons.Default.Build
    }
    @Composable
    override fun StatusVectosIcon(): IconVector {
        return IconVector(imageVector = Icons.Default.Build, contentDescription = "Setting", tint = Color.Cyan)
    }
}

fun fields ():List<ReportField>{
    return listOf(
        ReportField(name = "Address", headerTitle = LocalizationManager.getTranslation("name"), headerTextColor = Color.DarkGray, headerBackgroundColor = Color.Yellow, type = FieldTypeHelper.TEXT)
    )
}

@Composable
fun ShowResult(
    cursor: Cursor,
    label: String,
    fieldsDescr: List<ReportField>,
    withRowNumber: Boolean,
    alternatingRowColors: Boolean
) {
    Column {
        StyledButton(
            onClick = {
                openGeneratedPdfFromCursor(
                    cursor,
                    label,
                    fieldsDescr,
                    withRowNumber
                )
            }
        ) {
            Text(stringResource(R.string.open_pdf))
        }

        AutoDisplayCursorResult(
            cursor,
            label,
            fieldsDescr,
            withRowNumber,  // Rows numbering parameter
            alternatingRowColors
        )


    }

}