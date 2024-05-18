package cz.cvut.fel.sit.pda.screens.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cz.cvut.fel.sit.pda.R
import cz.cvut.fel.sit.pda.ui.theme.Purple800

/**
 * Composable function for displaying the version screen.
 *
 * @param onDismiss Callback function to be invoked when the dialog is dismissed.
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
            title = {
                Text(
                    text = "App Version"
                )
            },
            text = {
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.version),
                        contentDescription = "Version Icon",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "v.1.0.1",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Purple800),
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    showVersionDialog.value = false
                    onDismiss()
                }) {
                    Text(
                        text = "OK"
                    )
                }
            }
        )
    }
}