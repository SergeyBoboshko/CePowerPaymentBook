package io.github.sergeyboboshko.cereport.screens

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
//import androidx.compose.runtime.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import io.github.sergeyboboshko.composeentity.daemons.*
import io.github.sergeyboboshko.composeentity.daemons.localization.LocalizationManager


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldTopCommon() {
    val navController = GlobalContext.mainViewModel?.navController
    var viewModel = GlobalContext.mainViewModel

   TopAppBar(
        title = { Text(text = stringResource(io.github.sergeyboboshko.cereport.R.string.app_name)) },
        navigationIcon = {
            //------------------------- RETURN ICON ---------------------------------
            var showEscapeDialog by remember { mutableStateOf(false) }
            // Если есть предыдущий экран, покажем иконку назад
            IconButton(onClick = {
                if (navController?.previousBackStackEntry != null) {
                    //GlobalContext.mainViewModel?.blockCleans?:false==true means we don't need show showEscapeDialog and just step back
                    if (!GlobalContext.isModified||GlobalContext.mainViewModel?.blockCleans?:false==true) {
                        navController.popBackStack(mainCustomStack)
//                        if (GlobalContext.mainViewModel?.blockCleans?:false==false) {
//                            viewModel?.clearAllOfSelectedLists()
//                        }
//                        else{
//                            GlobalContext.mainViewModel?.blockCleans=false
//                            Toast.makeText(GlobalContext.context,"SAVED SETTINGS", Toast.LENGTH_SHORT).show()
//                        }
                        NavigationTargets.postpone()
                    } else {
                        //if (GlobalContext.mainViewModel?.blockCleans?:false==false) {
                            showEscapeDialog = true
                        //}
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
            //**************** DIALOG STEP BACK (ESCAPE SCREEN) **********************
            if (showEscapeDialog) {
                SimpleAlertDialog(
                    onDismissRequest = {
                        showEscapeDialog = false
                    },
                    onConfirmation = {
                        val currBlockCleans = GlobalContext.mainViewModel?.blockCleans?:false//save for return statuse if user will want to open settings after popBackStack
                        navController?.popBackStack(mainCustomStack)
                        GlobalContext.mainViewModel?.blockCleans = currBlockCleans
//                        if (GlobalContext.mainViewModel?.blockCleans?:false==false) {
//                            viewModel?.clearAllOfSelectedLists()
//                        }
//                        else{
//                            GlobalContext.mainViewModel?.blockCleans=false
//                        }
                        showEscapeDialog = false
                    },
                    dialogTitle = LocalizationManager.getTranslation("escape_screen"),
                    dialogText = LocalizationManager.getTranslation("escape_message"),
                    icon = Icons.Default.Delete,
                    applicationContext = GlobalContext.context,
                    id = 1, viewModel = viewModel, navController = navController
                )
            }

        },
        actions = {
            var showEscapeDialog by remember { mutableStateOf(false) }
            if (showEscapeDialog) {
                SimpleAlertDialog(
                    onDismissRequest = {
                        showEscapeDialog = false
                    },
                    onConfirmation = {
                        navController?.navigate("settings")
                        showEscapeDialog = false
                    },
                    dialogTitle = LocalizationManager.getTranslation("escape_screen"),
                    dialogText = LocalizationManager.getTranslation("escape_message"),
                    icon = Icons.Default.Delete,
                    applicationContext = GlobalContext.context,
                    id = 1, viewModel = viewModel, navController = navController
                )
            }
//            // Элементы в верхней панели
//            IconButton(onClick = {
//                val entity = FreeEntityExt(FreeEntity(0, -1L))
//                GlobalContext.mainViewModel?.uniSelectionManager?.clearSelection()
//                GlobalContext.mainViewModel?.uniSelectionManager?.toggleSelection(entity)
//                navController.navigate(
//                    SelfNav.getMainScreen(), mainCustomStack,
//                    FilterRoutineUI(context)
//                )
//            }) {
//                if (GlobalContext?.mainViewModel?.globalConditions?.isEmpty() ?: true) {
//                    Icon(
//                        painter = painterResource(id = R.mipmap.filter_alt_off_24px),
//                        contentDescription = null
//                    )
//                } else {
//                    Icon(
//                        painter = painterResource(id = R.mipmap.filter_alt_24px),
//                        tint = Color.Red,
//                        contentDescription = "Filter Had Set"
//                    )
//                }
//            }
            IconButton(onClick = {
                if(!GlobalContext.isModified){
                    navController?.navigate("settings")
                }
                else{
                    showEscapeDialog=true
                }
            }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings"/*, tint = GlobalColors.currentPalette.text*/)
            }
        }
    )

}