package cz.cvut.fel.sit.pda.screens.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.components.BasicAppBar
import cz.cvut.fel.sit.pda.components.GeldsBottomBar
import cz.cvut.fel.sit.pda.database.TransactionType
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.ui.theme.DefaultColor

@Composable
fun CategoriesScreen(navController: NavHostController, transactions: List<Transaction>) {
    var isExpensesSelected by remember { mutableStateOf(true) }
    val filteredTransactions = transactions.filter {
        it.type.category == if (isExpensesSelected) "Expenses" else "Income"
    }
    val groupedTransactions = filteredTransactions.groupBy { it.type }
        .toSortedMap(compareBy { it.displayName })

    Scaffold(
        topBar = {
            BasicAppBar(
                title = if (isExpensesSelected) "Expenses" else "Income",
                navController = navController,
                canNavigateBack = false,
                onNavigateBack = { }
            )
        },
        bottomBar = {
            GeldsBottomBar(navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DefaultColor),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                RoundedButton(
                    isExpensesSelected = isExpensesSelected,
                    onToggle = { isExpensesSelected = !isExpensesSelected }
                )
                Spacer(modifier = Modifier.height(32.dp))

                CategoriesGrid(isExpensesSelected, TransactionType.entries, groupedTransactions)

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
