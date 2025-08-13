package io.github.sergeyboboshko.ceppb

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.sergeyboboshko.ceppb.daemons.InitialDataPrompt
import io.github.sergeyboboshko.ceppb.daemons.initialLocales
import io.github.sergeyboboshko.ceppb.data.AppDatabase
import io.github.sergeyboboshko.composeentity.daemons.GlobalColors
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.InitComposableEntityVariables
import io.github.sergeyboboshko.composeentity.daemons.InitComposeEntityColors
import io.github.sergeyboboshko.composeentity.daemons.MainViewModel
import io.github.sergeyboboshko.composeentity.daemons.SelfNavigation
import io.github.sergeyboboshko.composeentity.daemons.screens.BottomCommonBar
import io.github.sergeyboboshko.composeentity_ksp.base.CeDatabaseVersion
import io.github.sergeyboboshko.ceppb.screens.MainPage
import io.github.sergeyboboshko.ceppb.screens.OtherNavigation
import io.github.sergeyboboshko.ceppb.screens.ScaffoldTopCommon
import io.github.sergeyboboshko.ceppb.screens.initialCustomColorPalette
import io.github.sergeyboboshko.ceppb.ui.theme.ComposeEntitySampleTheme
import io.github.sergeyboboshko.composeentity.daemons.GlobalConfig
import io.github.sergeyboboshko.composeentity_ksp.registerGlobalEntities
import kotlin.getValue


@CeDatabaseVersion(version = 1)
class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalContext.mainViewModel=viewModel
        //*** Define database
        val appDb = AppDatabase(this)
        val database = appDb.writableDatabase
        GlobalContext.database = database
        registerGlobalEntities()
        //***************************
        //GlobalContext.dropdownMenyStyle= DropdownMenuStyles.TILES
        enableEdgeToEdge()
        setContent {
            GlobalConfig.showTransferDB = false
            //****************************************************
            //use only in this direct order
            InitComposableEntityVariables()//has sence only before done next row "GlobalContext.init(this)"
            GlobalContext.init(this)
            InitComposeEntityColors()//has sence only after done next row "GlobalContext.init(this)"
            initialLocales()
            initialCustomColorPalette(LocalContext.current)
            InitialDataPrompt()
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

                            OtherNavigation()
                        }
                    }
                }
            }
        }
    }
}