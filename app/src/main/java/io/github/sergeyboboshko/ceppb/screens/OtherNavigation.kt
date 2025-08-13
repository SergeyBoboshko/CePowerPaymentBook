package io.github.sergeyboboshko.ceppb.screens

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.sergeyboboshko.ceppb.daemons.DeleteAllDataScreen
import io.github.sergeyboboshko.ceppb.daemons.InitialDataPrompt
import io.github.sergeyboboshko.ceppb.daemons.InitialDataState
import io.github.sergeyboboshko.composeentity.daemons.SettingsScreen
import io.github.sergeyboboshko.composeentity.daemons.dbtransfer.DatabaseFunctions
import io.github.sergeyboboshko.composeentity_ksp.base.Generated
import io.github.sergeyboboshko.composeentity_ksp.db.DependenciesProvider


fun NavGraphBuilder.OtherNavigation () {
    composable("init_db"){
        InitialDataState.markAsUnFilled(LocalContext.current)
        InitialDataPrompt()
    }

    composable (route="settings"){
        SettingsScreen(Generated.CeDatabaseVersion, DependenciesProvider as DatabaseFunctions)
    }

    composable (route="locate_settings"){ TopScreenSettings() }
    composable(route = "app_settings") { AppSettings() }
    composable(route="delete_all_data") { DeleteAllDataScreen() }
    composable(route="help_screen") { HelpScreen() }
}