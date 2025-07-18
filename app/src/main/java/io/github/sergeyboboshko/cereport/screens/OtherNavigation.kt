package io.github.sergeyboboshko.cereport.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.sergeyboboshko.cereport.daemons.DeleteAllDataScreen
import io.github.sergeyboboshko.cereport.daemons.InitialDataPrompt
import io.github.sergeyboboshko.cereport.daemons.InitialDataState
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
}