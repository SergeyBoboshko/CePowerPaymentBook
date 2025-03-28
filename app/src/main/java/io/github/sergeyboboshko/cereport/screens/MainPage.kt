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

import androidx.compose.ui.res.vectorResource

import io.github.sergeyboboshko.composeentity.daemons.BaseUI

import io.github.sergeyboboshko.composeentity.daemons.ClassicButtons

import io.github.sergeyboboshko.composeentity.daemons.SelfNav

import io.github.sergeyboboshko.cereport.details.DetailsAddressEntityUI

import io.github.sergeyboboshko.cereport.details.DetailsMeterEntityUI
import io.github.sergeyboboshko.cereport.references.*
import io.github.sergeyboboshko.cereport.R
import io.github.sergeyboboshko.cereport.documents.DocUtilityChargeUI
import io.github.sergeyboboshko.cereport.documents.DocUtilityPaymentUI


import java.util.Locale


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainPage(form: String) {
    LazyColumn()
    {
        item {
            ClassicButtons.NavigationButton(
                SelfNav.getMainScreen(),
                RefAddressesEntityUI() as BaseUI,
                "Addresses Reference",
                null
            )
        }
        item {
            ClassicButtons.NavigationButton(
                SelfNav.getMainScreen(),
                RefUtilitiseEntityUI() as BaseUI,
                "Utilities Reference",
                "Services of Utilities"
            )
        }
        item {
            FlowRow {
                ClassicButtons.IconNavigationButton(
                    commonText = "Address Details",
                    icon = ImageVector.vectorResource(id = R.drawable.roofing_24px),
                    routePath = SelfNav.getMainScreen(),
                    ui = DetailsAddressEntityUI() as BaseUI,
                    subText = ""
                )
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
            }
        }
        item { HorizontalDivider() }

        item {
            ClassicButtons.NavigationButton(
                SelfNav.getMainScreen(),
                DocUtilityChargeUI() as BaseUI,
                "Utility Charge Document",
                null
            )
        }
        item{
            ClassicButtons.NavigationButton(
                SelfNav.getMainScreen(),
                DocUtilityPaymentUI() as BaseUI,
                "Utility Payment Document",
                null
            )
        }

    }
}