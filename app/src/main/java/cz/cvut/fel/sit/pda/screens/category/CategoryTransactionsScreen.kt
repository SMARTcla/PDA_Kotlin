package cz.cvut.fel.sit.pda.screens.category

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.components.BasicAppBar
import cz.cvut.fel.sit.pda.components.GeldsBottomBar
import cz.cvut.fel.sit.pda.database.TransactionType
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.screens.transactions.TransactionDateHeader
import cz.cvut.fel.sit.pda.screens.transactions.TransactionItem
import cz.cvut.fel.sit.pda.ui.theme.DefaultColor
import cz.cvut.fel.sit.pda.ui.theme.DeepPurple500

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryTransactionsScreen(navController: NavHostController, transactions: List<Transaction>, category: TransactionType) {
    val filteredTransactions = transactions.filter { it.type == category }
    val groupedTransactions = filteredTransactions.groupBy { it.date }
        .toSortedMap(compareByDescending { it })

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = DefaultColor
    ) {
        Scaffold(
            topBar = {
                BasicAppBar(
                    title = category.displayName,
                    navController = navController,
                    canNavigateBack = true,
                    onNavigateBack = { navController.popBackStack() }
                )
            },
            bottomBar = {
                GeldsBottomBar(navController)
            },
            floatingActionButtonPosition = FabPosition.Center,
            content = { padding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(DefaultColor)
                ) {
                    groupedTransactions.forEach { (date, transactionsForDate) ->
                        stickyHeader {
                            TransactionDateHeader(date, transactionsForDate.sumOf { it.amount })
                        }
                        items(transactionsForDate) { transaction ->
                            TransactionItem(transaction, navController)
                        }
                    }
                }
            }
        )
    }
}
