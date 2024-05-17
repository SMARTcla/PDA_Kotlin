package cz.cvut.fel.sit.pda.screens.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cz.cvut.fel.sit.pda.R
import cz.cvut.fel.sit.pda.ui.theme.Indigo50
import cz.cvut.fel.sit.pda.ui.theme.Purple80

@Composable
fun SupportDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Support") },
        text = {
            Column {
                Row {

                    IconButton(onClick = {
                        val intent =
                            Intent(Intent.ACTION_SENDTO,
                                Uri.parse("mailto:krossale@fel.cvut.cz"))
                        context.startActivity(intent)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.message),
                            contentDescription = "Email Icon",
                            tint = Indigo50
                        )
                    }
                    Text(

                        text = "Click to Icon message",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Purple80),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 14.dp)
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