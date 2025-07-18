package io.github.sergeyboboshko.cereport.screens

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.StyledButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun TopScreenSettings(){
    val navController = GlobalContext.mainViewModel?.navController
    var viewModel = GlobalContext.mainViewModel
    LazyColumn {
        item{
            StyledButton(onClick = {navController?.navigate("settings")}){
                Text("System Settings")
            }
        }
        item{
            StyledButton(onClick = {navController?.navigate("app_settings")}){
                Text("App Settings")
            }
        }
        item{
            StyledButton(onClick = {navController?.navigate("delete_all_data")}){
                    Text("Delete all data")
            }
        }
    }
}