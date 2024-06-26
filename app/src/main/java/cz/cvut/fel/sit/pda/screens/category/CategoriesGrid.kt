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

/**
 * Composable function for displaying a grid of categories.
 *
 * @param isExpensesSelected Flag indicating whether expenses are selected.
 * @param categories List of transaction categories.
 * @param transactionSums Map of transaction types to their sums.
 * @param onCategoryClick Callback function for when a category is clicked.
 */
@Composable
fun CategoriesGrid(
    isExpensesSelected: Boolean,
    categories: List<TransactionType>,
    transactionSums: Map<TransactionType, List<Transaction>>,
    onCategoryClick: (TransactionType) -> Unit
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
                    CategoryIcon(
                        category = category,
                        sum = transactionSums[category]?.sumOf { it.amount } ?: 0L,
                        onClick = { onCategoryClick(category) }
                    )
                }
            }
        }
    }
}
