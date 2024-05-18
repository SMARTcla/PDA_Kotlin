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
import cz.cvut.fel.sit.pda.database.TransactionType

/**
 * Composable function to display a dropdown menu for selecting transaction types.
 *
 * @param selectedType The currently selected transaction type.
 * @param onTypeSelected Callback function to be invoked when a transaction type is selected.
 * @param transactionTypes List of available transaction types to display in the dropdown.
 */
@Composable
fun DropdownMenu(
    selectedType: TransactionType,
    onTypeSelected: (TransactionType) -> Unit,
    transactionTypes: List<TransactionType>
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
                .background(
                    MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth()
        ) {
            if (showHint) {
                Text(
                    text = "Select Category",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            } else {
                Text(
                    text = selectedType.displayName,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.background(
                    MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(4.dp)
                )
            ) {
                transactionTypes.forEach { type ->
                    DropdownMenuItem(
                        onClick = {
                            onTypeSelected(type)
                            showHint = false
                            expanded = false
                        },
                        text = {
                            Text(
                                type.displayName,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    )
                }
            }
        }
    }
}
