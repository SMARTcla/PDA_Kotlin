package cz.cvut.fel.sit.pda.screens.budget

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BudgetExpenses(icon: ImageVector, amountSpent: Int, budgeted: Int) {
    BudgetCategory(title = "Expenses", icon = icon, amountSpent = amountSpent, budgeted = budgeted)
}
