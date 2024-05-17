package cz.cvut.fel.sit.pda.screens.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.cvut.fel.sit.pda.database.TransactionType
import cz.cvut.fel.sit.pda.models.Transaction

@Composable
fun CategoriesGrid(
    isExpensesSelected: Boolean,
    categories: List<TransactionType>,
    transactionSums: Map<TransactionType, List<Transaction>>
) {
    val filteredCategories =
        categories.filter { it.category == if (isExpensesSelected) "Expenses" else "Income" }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        filteredCategories.chunked(3).forEach { rowCategories ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowCategories.forEach { category ->
                    CategoryIcon(category, transactionSums[category]?.sumOf { it.amount } ?: 0L)
                }
            }
        }
    }
}