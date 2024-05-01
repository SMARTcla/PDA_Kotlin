package cz.cvut.fel.sit.pda.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.cvut.fel.sit.pda.models.Transaction

@Composable
fun TransactionItem(transaction: Transaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = transaction.name, style = MaterialTheme.typography.h6)
                Text(text = transaction.type.displayName, style = MaterialTheme.typography.caption)
            }
            Text(text = "${transaction.amount} CZK", style = MaterialTheme.typography.h6)
        }
    }
}
