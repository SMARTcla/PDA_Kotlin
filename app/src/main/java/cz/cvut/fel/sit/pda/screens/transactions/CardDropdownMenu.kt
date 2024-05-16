package cz.cvut.fel.sit.pda.screens.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.cvut.fel.sit.pda.database.BankEntity
import cz.cvut.fel.sit.pda.models.BankCard

@Composable
fun CardDropdownMenu(
    selectedCard: String,
    onCardSelected: (String) -> Unit,
    bankCards: List<BankEntity>
) {
    var expanded by remember { mutableStateOf(false) }
    var showHint by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clickable { expanded = true }
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(4.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth()
        ) {
            if (showHint) {
                Text(
                    text = "Select Bank Card",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            } else {
                Text(
                    text = selectedCard,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(4.dp))
            ) {
                bankCards.forEach { card ->
                    DropdownMenuItem(
                        onClick = {
                            onCardSelected(card.name)
                            showHint = false
                            expanded = false
                        },
                        text = {
                            Text(card.name, color = MaterialTheme.colorScheme.onSurface)
                        }
                    )
                }
            }
        }
    }
}
