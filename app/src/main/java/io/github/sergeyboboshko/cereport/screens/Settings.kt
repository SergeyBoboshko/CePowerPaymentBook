package io.github.sergeyboboshko.cereport.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import io.github.sergeyboboshko.cereport.R
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = ImageVector.vectorResource(id= R.drawable.settings_applications_40px), contentDescription = "System Settings")
                    Spacer(modifier = Modifier.width(8.dp)) // для відступу між прапорцем і текстом
                    Text("System Settings")
                }
            }
        }
        item{
            StyledButton(onClick = {navController?.navigate("app_settings")}){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = ImageVector.vectorResource(id= R.drawable.settings_motion_mode_40px), contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp)) // для відступу між прапорцем і текстом
                    Text("Color palette settings")
                }
            }
        }
        item{
            StyledButton(onClick = {navController?.navigate("delete_all_data")}){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete DB and Preferences")
                    Spacer(modifier = Modifier.width(8.dp)) // для відступу між прапорцем і текстом
                    Text("Delete all data")
                }
            }
        }
        item{
            StyledButton(onClick = {navController?.navigate("help_screen")}){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = ImageVector.vectorResource(id= R.drawable.help_center_40px), contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp)) // для відступу між прапорцем і текстом
                    Text("Help")
                }

            }
        }
    }
}