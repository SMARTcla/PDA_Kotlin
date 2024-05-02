package cz.cvut.fel.sit.pda.screens.budget

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BudgetExpenses(icon: ImageVector, amountSpent: Int, budgeted: Int) {
    BudgetCategory(title = "Expenses", icon = icon, amountSpent = amountSpent, budgeted = budgeted)
}
