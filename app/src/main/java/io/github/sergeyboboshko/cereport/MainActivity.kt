package io.github.sergeyboboshko.cereport

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.sergeyboboshko.composeentity.daemons.DropdownMenuStyles
import io.github.sergeyboboshko.composeentity.daemons.GlobalColors
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.GlobalState
import io.github.sergeyboboshko.composeentity.daemons.InitComposableEntityVariables
import io.github.sergeyboboshko.composeentity.daemons.InitComposeEntityColors
import io.github.sergeyboboshko.composeentity.daemons.MainViewModel
import io.github.sergeyboboshko.composeentity.daemons.SelfNavigation
import io.github.sergeyboboshko.composeentity.daemons.SettingsScreen
import io.github.sergeyboboshko.composeentity.daemons.screens.BottomCommonBar
import io.github.sergeyboboshko.composeentity_ksp.base.DatabaseVersion
import io.github.sergeyboboshko.cereport.screens.MainPage
import io.github.sergeyboboshko.cereport.screens.ScaffoldTopCommon
import io.github.sergeyboboshko.cereport.ui.theme.ComposeEntitySampleTheme
import kotlin.getValue


@DatabaseVersion(version = 1)
class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalContext.mainViewModel=viewModel
        //GlobalContext.dropdownMenyStyle= DropdownMenuStyles.TILES
        enableEdgeToEdge()
        setContent {
            //****************************************************
            //використати тільки в такій послідовності
            InitComposableEntityVariables()//має сенс тільки до GlobalContext.init(this)
            GlobalContext.init(this)
            InitComposeEntityColors()//має сенс тільки після GlobalContext.init(this)
            //*****************************************************************
            var navController = rememberNavController()
            GlobalContext.mainViewModel?.navController = navController
            //GlobalContext.context = this
            Log.d("S_TEST","GlobalContext.mainViewModel = ${GlobalContext.mainViewModel}")
            ComposeEntitySampleTheme(darkTheme = GlobalContext.darkMode) {
                Scaffold(modifier = Modifier.fillMaxSize()
                    .background(color = GlobalColors.currentPalette.background),
                    topBar = {
                        ScaffoldTopCommon()
                    },
                    bottomBar = { BottomCommonBar() }) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(color = GlobalColors.currentPalette.background)
                    ) {
                        NavHost(navController = navController, startDestination = "home") {
                            composable("home") {
                                MainPage("MainList")
                            }

                            composable(route = "selfNav/{form}",
                                arguments = listOf(
                                    navArgument("form") { type = NavType.StringType }
                                )) {
                                //NavigationTargets.current = "selfNav"
                                val form =
                                    navController.currentBackStackEntry?.arguments?.getString("form")
                                SelfNavigation(form ?: "")
                            }

                            composable (route="settings"){
                                SettingsScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}