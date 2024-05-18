package cz.cvut.fel.sit.pda.screens.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.cvut.fel.sit.pda.database.TransactionType
import cz.cvut.fel.sit.pda.ui.theme.Green700
import cz.cvut.fel.sit.pda.ui.theme.Grey200
import cz.cvut.fel.sit.pda.ui.theme.Pink800
import cz.cvut.fel.sit.pda.ui.theme.black

/**
 * Composable function for displaying a transaction type item.
 *
 * @param transactionType The transaction type.
 * @param totalAmount The total amount associated with the transaction type.
 * @param onClick Callback function to be invoked when the item is clicked.
 */
@Composable
fun TransactionTypeItem(transactionType: TransactionType, totalAmount: Long, onClick: () -> Unit) {

    val amountColor = if (transactionType.category == "Expenses") Pink800 else Green700

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(70.dp)
            .background(Grey200, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = transactionType.displayName,
                color = black,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "$totalAmount CZK",
                color = amountColor,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}