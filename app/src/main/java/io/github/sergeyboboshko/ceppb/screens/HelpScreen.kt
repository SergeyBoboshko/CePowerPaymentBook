package io.github.sergeyboboshko.ceppb.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun HelpScreen() {
    val uriHandler = LocalUriHandler.current
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Help & Information", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "This application is open source. You can use, modify, and distribute it as you wish. " +
                    "It does not collect any user data, and it is not aware of who might collect or use your data externally.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "GitHub Repository",
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                uriHandler.openUri("https://github.com/SergeyBoboshko/CePowerPaymentBook/tree/main") // заміни на свій лінк
            }
        )
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            "Privacy Policy",
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                uriHandler.openUri("https://github.com/SergeyBoboshko/CePowerPaymentBook/blob/main/PRIVACY_POLICY.md") // лінк на політику приватності
            }
        )
    }
}
