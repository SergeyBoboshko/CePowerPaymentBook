package io.github.sergeyboboshko.cereport.alerts

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CleanAndRefillDialodue (onConfirm:()->Unit,onDismiss:()->Unit){
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
           TextButton(onClick = onConfirm) {
               Text("Confirm")
           }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Dismiss")
            }
        },
        icon = { Icons.Default.Refresh},
        title = { Text("Warning!") },
        text = { Text("The details will be cleared and refilled — this action cannot be undone.") }
    )
}