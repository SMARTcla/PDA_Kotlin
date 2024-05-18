package cz.cvut.fel.sit.pda.screens.settings.receipts

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

/**
 * Composable function for displaying the dialog with user's receipts.
 *
 * @param imageUri URI of the image representing the receipt.
 * @param onDismiss Callback function to be invoked when the dialog is dismissed.
 */
@Composable
fun MyReceiptsDialog(imageUri: Uri, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "My Receipts") },
        text = {
            AsyncImage(
                model = imageUri,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "OK")
            }
        }
    )
}
