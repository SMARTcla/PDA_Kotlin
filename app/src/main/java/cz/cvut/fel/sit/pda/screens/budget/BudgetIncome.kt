package cz.cvut.fel.sit.pda.screens.budget

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BudgetIncome(icon: ImageVector, amountSpent: Int, budgeted: Int) {
    BudgetCategory(title = "Income", icon = icon, amountSpent = amountSpent, budgeted = budgeted)
}