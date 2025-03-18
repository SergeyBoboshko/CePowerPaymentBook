package io.github.sergeyboboshko.cereport.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.sergeyboboshko.composeentity.daemons.BaseUI
import io.github.sergeyboboshko.composeentity.daemons.ButtonDisplayMode
import io.github.sergeyboboshko.composeentity.daemons.ClassicButtons
import io.github.sergeyboboshko.composeentity.daemons.GlobalColors
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext.darkMode
import io.github.sergeyboboshko.composeentity.daemons.SelfNav
import io.github.sergeyboboshko.composeentity.daemons.StyledButton
import io.github.sergeyboboshko.composeentity.daemons.localization.LocalizationManager
import io.github.sergeyboboshko.composeentity.daemons.navigate
import io.github.sergeyboboshko.composeentity.documents.base.DocumentsIconButton
import io.github.sergeyboboshko.cereport.MyApplication1
import io.github.sergeyboboshko.cereport.details.DetailsAddressEntityUI
import io.github.sergeyboboshko.cereport.references.RefAddressesEntityUI
import io.github.sergeyboboshko.cereport.references.RefUtilitiseEntityUI


import java.util.Locale


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainPage (form:String){
   // val references = LocalizationManager.getTranslation("references")
    //val documents = LocalizationManager.getTranslation("documents")
    //WelcomeScreen()
    FlowRow {
        ClassicButtons.NavigationButton(SelfNav.getMainScreen(), RefAddressesEntityUI() as BaseUI,"Addresses Reference",null)
        ClassicButtons.NavigationButton(SelfNav.getMainScreen(), RefUtilitiseEntityUI() as BaseUI,"Utilities Reference","Services of Utilities")
        ClassicButtons.IconNavigationButton(commonText = "Address Details", icon = Icons.Default.Menu, routePath = SelfNav.getMainScreen(),ui=DetailsAddressEntityUI() as BaseUI, subText = "")
    }
}