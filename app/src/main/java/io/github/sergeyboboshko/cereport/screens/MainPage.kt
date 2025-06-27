package io.github.sergeyboboshko.cereport.screens

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import io.github.sergeyboboshko.composeentity.daemons.BaseUI
import io.github.sergeyboboshko.composeentity.daemons.buttons.ClassicButtons
import io.github.sergeyboboshko.composeentity.daemons.SelfNav
import io.github.sergeyboboshko.cereport.details.DetailsAddressEntityUI
import io.github.sergeyboboshko.cereport.details.DetailsMeterEntityUI
import io.github.sergeyboboshko.cereport.references.*
import io.github.sergeyboboshko.cereport.R
import io.github.sergeyboboshko.cereport.accumulationregisters.ARegPaymentsUI
import io.github.sergeyboboshko.cereport.documents.DocSubsidyUI
import io.github.sergeyboboshko.cereport.documents.DocUtilityChargeUI
import io.github.sergeyboboshko.cereport.documents.DocUtilityPaymentUI
import io.github.sergeyboboshko.cereport.reports.ReportUtilityPaymentsFreeEntityUI
import io.github.sergeyboboshko.composeentity.daemons.GlobalState
import io.github.sergeyboboshko.composeentity.daemons.IconAligment


import java.util.Locale


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
                    icon = ImageVector.vectorResource(R.drawable.payments_24px),
                    iconSize = 48.dp,
                    iconAligment = IconAligment.LEFT
                )
            }
        }

        item { HorizontalDivider() }

        item{
            ClassicButtons.NavigationButton(
                routePath = SelfNav.getMainScreen(),
                ui = ReportUtilityPaymentsFreeEntityUI() as BaseUI,
                caption = stringResource(R.string.payment_balance),
                null
            )
        }

    }
}