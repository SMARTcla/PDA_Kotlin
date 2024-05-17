package cz.cvut.fel.sit.pda.screens.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import cz.cvut.fel.sit.pda.R
import cz.cvut.fel.sit.pda.ui.theme.*

@Composable
fun AboutDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "About Us"
            )
        },
        text = {
            Column {

                Row {

                    IconButton(onClick = {
                        val intent =
                            Intent(Intent.ACTION_SENDTO,
                                Uri.parse("https://github.com/SMARTcla/PDA_Kotlin"))
                        context.startActivity(intent)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.github),
                            contentDescription = "Github Icon",
                            tint = Indigo50
                        )
                    }
                    Text(

                        text = "Click to Github icon",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Purple80),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 14.dp)
                    )

                }

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Authors:",
                    style = MaterialTheme.typography.bodyLarge.copy
                        (color = Purple80),
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Aleksandr Kross",
                    style = MaterialTheme.typography.bodyLarge,
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Misha Kononenko",
                    style = MaterialTheme.typography.bodyLarge,
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