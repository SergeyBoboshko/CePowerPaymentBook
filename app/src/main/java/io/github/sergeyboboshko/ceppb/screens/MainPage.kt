package io.github.sergeyboboshko.ceppb.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.sergeyboboshko.composeentity.daemons.BaseUI
import io.github.sergeyboboshko.composeentity.daemons.buttons.ClassicButtons
import io.github.sergeyboboshko.composeentity.daemons.SelfNav
import io.github.sergeyboboshko.ceppb.details.DetailsMeterEntityUI
import io.github.sergeyboboshko.ceppb.references.*
import io.github.sergeyboboshko.ceppb.R
import io.github.sergeyboboshko.ceppb.accumulationregisters.ARegPaymentsUI
import io.github.sergeyboboshko.ceppb.documents.DocSubsidyUI
import io.github.sergeyboboshko.ceppb.documents.DocTariffRegistryUI
import io.github.sergeyboboshko.ceppb.documents.DocUtilityChargeUI
import io.github.sergeyboboshko.ceppb.documents.DocUtilityPaymentUI
import io.github.sergeyboboshko.ceppb.informationregisters.TariffsUI
import io.github.sergeyboboshko.ceppb.reports.ReportUtilityPaymentsFreeEntityUI
import io.github.sergeyboboshko.composeentity.daemons.ButtonDisplayMode
import io.github.sergeyboboshko.composeentity.daemons.ComposeEntity
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.GlobalState
import io.github.sergeyboboshko.composeentity.daemons.IconAligment
import io.github.sergeyboboshko.composeentity.daemons.StyledButton
import io.github.sergeyboboshko.composeentity_ksp.ComposeEntityKSP


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainPage(form: String) {
    GlobalState.hideAllBottomBarButtons()
    LazyColumn()
    {
        item {
            FlowRow {
            ClassicButtons.IconAndTextNavigationButton(
                routePath = SelfNav.getMainScreen(),
                ui = RefAddressesEntityUI() as BaseUI,
                commonText = "Addresses Reference",
                subText = "List of your tracked addresses",
                icon = ImageVector.vectorResource(id= R.drawable.signpost_24px),
                iconSize = 48.dp
            )


            ClassicButtons.IconAndTextNavigationButton(
                routePath = SelfNav.getMainScreen(),
                ui = RefUtilitiseEntityUI() as BaseUI,
                commonText = "Utilities Reference",
                subText = "Services of Utilities",
                icon = ImageVector.vectorResource(R.drawable.wifi_home_24px),
                iconSize = 48.dp,
                iconAligment = IconAligment.RIGHT
            )

               /* ClassicButtons.IconNavigationButton(
                    commonText = "Address Details",
                    icon = ImageVector.vectorResource(id = R.drawable.roofing_24px),
                    routePath = SelfNav.getMainScreen(),
                    ui = DetailsAddressEntityUI() as BaseUI,
                    subText = ""
                )*/
            }
        }
        item {
            HorizontalDivider()
        }
        item {
            FlowRow {
                ClassicButtons.IconNavigationButton(
                    commonText = "Meters reference",
                    icon = ImageVector.vectorResource(id = R.drawable.gas_meter_24px),
                    routePath = SelfNav.getMainScreen(),
                    ui = RefMetersUI() as BaseUI,
                    subText = ""
                )
                ClassicButtons.IconNavigationButton(
                    commonText = "Types of Meters",
                    icon = Icons.Default.List,
                    routePath = SelfNav.getMainScreen(),
                    ui = RefTypesOfMetersUI() as BaseUI,
                    subText = ""
                )
                ClassicButtons.IconNavigationButton(
                    commonText = "Meter Zones",
                    icon = Icons.Default.List,
                    routePath = SelfNav.getMainScreen(),
                    ui = RefMeterZonesUI() as BaseUI,
                    subText = ""
                )

                ClassicButtons.IconNavigationButton(
                    commonText = "Details Meters",
                    icon = Icons.Default.Share,
                    routePath = SelfNav.getMainScreen(),
                    ui = DetailsMeterEntityUI() as BaseUI,
                    subText = ""
                )
                ClassicButtons.IconNavigationButton(
                    commonText = "Payment Balance",
                    icon = ImageVector.vectorResource(R.drawable.account_balance_24px),
                    routePath = SelfNav.getMainScreen(),
                    ui = ARegPaymentsUI() as BaseUI,
                    subText = ""
                )

            }
        }
        item { HorizontalDivider() }

        item {
            FlowRow {
                ClassicButtons.IconAndTextNavigationButton(
                    routePath = SelfNav.getMainScreen(),
                    ui = DocUtilityChargeUI() as BaseUI,
                    commonText = "Utility Charge Document",
                    subText = "Invoices expence list",
                    icon = ImageVector.vectorResource(R.drawable.price_check_24px),
                    iconSize = 48.dp
                )

                ClassicButtons.IconAndTextNavigationButton(
                    routePath = SelfNav.getMainScreen(),
                    ui = DocUtilityPaymentUI() as BaseUI,
                    commonText = "Utility Payment Document",
                    subText = "",
                    icon = ImageVector.vectorResource(R.drawable.payments_24px),
                    iconSize = 48.dp,
                    iconAligment = IconAligment.RIGHT
                )

                ClassicButtons.IconAndTextNavigationButton(
                    routePath = SelfNav.getMainScreen(),
                    ui = DocSubsidyUI() as BaseUI,
                    commonText = "Subsidy Payment Document",
                    subText = "",
                    icon = ImageVector.vectorResource(R.drawable.paid_48px),
                    iconSize = 48.dp,
                    iconAligment = IconAligment.LEFT
                )
                ClassicButtons.IconAndTextNavigationButton(
                    routePath = SelfNav.getMainScreen(),
                    ui = DocTariffRegistryUI() as BaseUI,
                    commonText = stringResource(R.string.doc_tariff_registry),
                    subText = "",
                    icon = ImageVector.vectorResource(R.drawable.trending_up_48px),
                    iconSize = 48.dp,
                    iconAligment = IconAligment.RIGHT
                )

            }
        }

        item { HorizontalDivider() }

        item{
            FlowRow {
                ClassicButtons.IconAndTextNavigationButton(
                    routePath = SelfNav.getMainScreen(),
                    ui = ReportUtilityPaymentsFreeEntityUI() as BaseUI,
                    commonText = stringResource(R.string.payment_balance),
                    icon = ImageVector.vectorResource(R.drawable.article_48px),
                    iconSize = 48.dp
                )
                StyledButton(icon = Icons.Default.Face, displayMode = ButtonDisplayMode.IconOnly, iconContentDescription = "INIT Fill",
                    onClick = {
                        //startInitializator()
                        GlobalContext.mainViewModel?.navController?.navigate("init_db")
                    })
                ClassicButtons.IconNavigationButton(
                    routePath = SelfNav.getMainScreen(),
                    ui = TariffsUI() as BaseUI,
                    commonText = stringResource(R.string.info_reg_tariffs_label),
                    icon = ImageVector.vectorResource(R.drawable.trending_up_48px),
                    iconSize = 48.dp
                )
            }
        }
        item{
            CenteredCopyrightText()
        }

    }
}

@Composable
fun CenteredCopyrightText() {
    Column(
        modifier = Modifier.fillMaxWidth(), // Розтягуємо Column на всю ширину
        horizontalAlignment = Alignment.CenterHorizontally // Вирівнюємо вміст по центру горизонтально
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp)) {
                    append("© 2025 Serhij Boboshko sergey.boboshko@gmail.com, all rights recived ${ComposeEntity.version()} (KSP: ${ComposeEntityKSP.version()})")
                }
            }
        )
        // Додаємо новий Text для "Powered By" у новому рядку
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Gray, fontSize = 12.sp)) {
                    append("Powered By Compose entity")
                }
            }
        )
    }
}