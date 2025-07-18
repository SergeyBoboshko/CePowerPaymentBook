package io.github.sergeyboboshko.cereport.daemons

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity_ksp.db.AppDatabase

// Your preferences:
private const val PREF_COLOR = "color_prefs"
private const val PREF_INITIAL = "initial_data_pref"

fun deleteAllData(context: Context) {
    // Delete the database
    context.deleteDatabase("app_database")

    // Manually clear preferences
    listOf(PREF_COLOR, PREF_INITIAL).forEach { prefName ->
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
    }
}

@Composable
fun DeleteAllDataScreen(
    onDataDeleted: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    val context = LocalContext.current
    var showConfirmDialog by remember { mutableStateOf(true) }
    var showDeletedMessage by remember { mutableStateOf(false) }
    var showCancelledMessage by remember { mutableStateOf(false) }

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false; showCancelledMessage = true },
            title = { Text("Delete all data?") },
            text = { Text("Are you sure you want to delete all app data? This action cannot be undone. Please make sure you have backups if needed.") },
            confirmButton = {
                TextButton(onClick = {
                    deleteAllData(context)
                    showConfirmDialog = false
                    showDeletedMessage = true
                    onDataDeleted()
                }) {
                    Text("Yes, delete")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showConfirmDialog = false
                    showCancelledMessage = true
                    onCancel()
                }) {
                    Text("Cancel")
                }
            }
        )
    } else if (showDeletedMessage) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("All data deleted. Please restart the app.", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { closeApp() }) {
                    Text("Exit app")
                }
            }
        }
    } else if (showCancelledMessage) {
        CancelledScreen(onBack = {
            // For example, hide this message and navigate somewhere
            showCancelledMessage = false
            GlobalContext.mainViewModel?.navigateToHome()
        })
    }

    BackHandler(enabled = showConfirmDialog) {
        // Block back button while dialog is shown
    }
}

fun closeApp() {
    // Forcefully terminate the app process
    android.os.Process.killProcess(android.os.Process.myPid())
    // The system will then close the app
}

@Composable
fun CancelledScreen(
    onBack: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Delete cancelled. Your data is safe.", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBack) {
                Text("Go back")
            }
        }
    }
}
