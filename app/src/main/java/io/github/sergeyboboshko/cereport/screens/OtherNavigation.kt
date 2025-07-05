package io.github.sergeyboboshko.cereport.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.sergeyboboshko.cereport.daemons.InitialDataPrompt
import io.github.sergeyboboshko.cereport.daemons.InitialDataState


fun NavGraphBuilder.OtherNavigation () {
    composable("init_db"){
        InitialDataState.markAsUnFilled(LocalContext.current)
        InitialDataPrompt()
    }
}