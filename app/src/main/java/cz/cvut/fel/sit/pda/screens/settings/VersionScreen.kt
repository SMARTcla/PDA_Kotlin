package cz.cvut.fel.sit.pda.screens.settings

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * A screen component to display the app version in a dialog.
 */
@Composable
fun VersionScreen(onDismiss: () -> Unit) {
    val showVersionDialog = remember { mutableStateOf(true) }

    if (showVersionDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showVersionDialog.value = false
                onDismiss()
            },
            title = { Text("App Version") },
            text = { Text("v.1.0.1") },
            confirmButton = {
                TextButton(onClick = {
                    showVersionDialog.value = false
                    onDismiss()
                }) {
                    Text("OK")
                }
            }
        )
    }
}
