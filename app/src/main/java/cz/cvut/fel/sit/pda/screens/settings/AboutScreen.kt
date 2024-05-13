package cz.cvut.fel.sit.pda.screens.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp

@Composable
fun AboutDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("About Us") },
        text = {
            Column {
                Text("Code source:")
                ClickableText(
                    text = AnnotatedString("https://github.com/SMARTcla/PDA_Kotlin"),
                    onClick = {
                        context.startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://github.com/SMARTcla/PDA_Kotlin")))
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Authors: Misha, Alex.")
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}