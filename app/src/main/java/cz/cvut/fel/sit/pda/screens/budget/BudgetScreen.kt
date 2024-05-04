package cz.cvut.fel.sit.pda.screens.budget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.components.BasicAppBar
import cz.cvut.fel.sit.pda.components.GeldsBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            BasicAppBar(
                title = "Budget",
                navController = navController,
                canNavigateBack = false,
                onNavigateBack = {}
            )
        },
        bottomBar = {
            GeldsBottomBar(navController)
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            BudgetExpenses(icon = Icons.Filled.ShoppingCart, amountSpent = 212, budgeted = 1000)
            BudgetSavings(icon = Icons.Filled.AddCircle, amountSpent = 100, budgeted = 500)
            BudgetIncome(icon = Icons.AutoMirrored.Filled.Send, amountSpent = 1000, budgeted = 3000)
        }
    }
}