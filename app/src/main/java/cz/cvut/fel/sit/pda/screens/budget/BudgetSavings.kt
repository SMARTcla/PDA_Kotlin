package cz.cvut.fel.sit.pda.screens.budget

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BudgetSavings(icon: ImageVector, amountSpent: Int, budgeted: Int) {
    BudgetCategory(title = "Savings", icon = icon, amountSpent = amountSpent, budgeted = budgeted)
}
