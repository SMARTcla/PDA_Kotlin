package cz.cvut.fel.sit.pda.screens.settings.receipts

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.compose.ui.unit.dp
import cz.cvut.fel.sit.pda.ui.theme.Purple80
import java.io.File

@Composable
fun AddReceiptsDialog(onDismiss: () -> Unit, onPictureSelected: (Uri) -> Unit) {
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { onPictureSelected(it) }
        onDismiss()
    }

    val photoUri = remember {
        val photoFile = File(context.cacheDir, "new_receipt_photo.jpg")
        FileProvider.getUriForFile(context, "${context.packageName}.provider", photoFile)
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            onPictureSelected(photoUri)
        }
        onDismiss()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add Receipt") },
        text = {
            Text(
                text = "Choose a picture from your gallery or take a new photo.",
                style = MaterialTheme.typography.bodyLarge.copy
                    (color = Purple80),
                )
        },
        confirmButton = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { galleryLauncher.launch("image/*") },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text(text = "Use gallery")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { cameraLauncher.launch(photoUri) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text(text = "Use Camera")
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = "Cancel")
                    }
                }
            }
        },
        dismissButton = {}
    )
}