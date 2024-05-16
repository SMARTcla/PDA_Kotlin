package cz.cvut.fel.sit.pda.screens.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.models.Transaction

@Composable
fun TransactionItem(transaction: Transaction, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("transactionDetail/${transaction.id}")
            },
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
