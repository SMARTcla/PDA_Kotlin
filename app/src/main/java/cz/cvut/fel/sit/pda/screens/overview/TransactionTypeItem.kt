package cz.cvut.fel.sit.pda.screens.overview

import androidx.compose.foundation.background
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
import cz.cvut.fel.sit.pda.ui.theme.*


@Composable
fun TransactionTypeItem(transactionType: TransactionType, totalAmount: Long) {

    val amountColor = if (transactionType.category == "Expenses") Pink800 else Green700

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(70.dp)
            .background(Grey200, RoundedCornerShape(8.dp))
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