package cz.cvut.fel.sit.pda.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Composable function for displaying an option item.
 *
 * @param text The text to display for the option.
 * @param onClick Callback function to be invoked when the option is clicked. Defaults to null.
 */
@Composable
fun OptionItem(text: String, onClick: (() -> Unit)? = null) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick ?: {})
            .padding(16.dp)
    )
}