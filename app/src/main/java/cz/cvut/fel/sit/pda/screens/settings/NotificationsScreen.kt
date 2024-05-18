package cz.cvut.fel.sit.pda.screens.settings

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cz.cvut.fel.sit.pda.services.NotificationService
import cz.cvut.fel.sit.pda.ui.theme.Purple800

/**
 * Composable function to display a dialog for managing notification settings.
 *
 * @param enableNotifications Boolean indicating whether notifications are enabled.
 * @param onEnableChange Callback function to be invoked when the enable notifications switch state changes.
 * @param onDismiss Callback function to be invoked when the dialog is dismissed.
 */
@Composable
fun NotificationsDialog(
    enableNotifications: Boolean,
    onEnableChange: (Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Notification Settings")
        },
        text = {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Switch Notifications",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Purple800),
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp)
                )
                Switch(
                    checked = enableNotifications,
                    onCheckedChange = { enabled ->
                        onEnableChange(enabled)
                        if (enabled) {
                            context.startService(Intent(context, NotificationService::class.java))
                        } else {
                            context.stopService(Intent(context, NotificationService::class.java))
                        }
                    }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "OK")
            }
        }
    )
}
