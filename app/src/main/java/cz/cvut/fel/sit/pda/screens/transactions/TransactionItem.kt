package cz.cvut.fel.sit.pda.screens.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.ui.theme.Green700
import cz.cvut.fel.sit.pda.ui.theme.Pink800

/**
 * Composable function to display a single transaction item.
 *
 * @param transaction The transaction to display.
 * @param navController The navigation controller used for navigating to transaction details.
 */
@Composable
fun TransactionItem(transaction: Transaction, navController: NavHostController) {

    val amountColor = if (transaction.type.category == "Expenses")
        Pink800 else Green700

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .clickable {
                navController.navigate("transactionDetail/${transaction.id}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = transaction.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = transaction.type.displayName,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = "${transaction.amount} CZK",
                style = MaterialTheme.typography.headlineSmall,
                color = amountColor
            )
        }
    }
}
