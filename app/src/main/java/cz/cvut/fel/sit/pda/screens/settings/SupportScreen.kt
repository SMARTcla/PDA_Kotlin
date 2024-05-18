package cz.cvut.fel.sit.pda.screens.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import cz.cvut.fel.sit.pda.R
import cz.cvut.fel.sit.pda.ui.theme.Purple800

/**
 * Composable function for displaying the support dialog.
 *
 * @param onDismiss Callback function to be invoked when the dialog is dismissed.
 */
@Composable
fun SupportDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Support") },
        text = {
            Column {
                Row {

                    Icon(
                        painter = painterResource(id = R.drawable.message),
                        contentDescription = "Email Icon",
                    )

                    ClickableText(
                        text = AnnotatedString("Send a message"),
                        style = MaterialTheme.typography.bodyLarge.copy(color = Purple800),
                        onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("mailto:krossale@fel.cvut.cz")
                            )
                            context.startActivity(intent)
                        },
                        modifier =
                        Modifier.padding(
                            horizontal = 14.dp,
                            vertical = 7.dp
                        )
                    )

                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Close")
            }
        }
    )
}