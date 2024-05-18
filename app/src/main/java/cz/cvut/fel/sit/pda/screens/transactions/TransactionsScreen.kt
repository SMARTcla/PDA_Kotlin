package cz.cvut.fel.sit.pda.screens.transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.GeldScreen
import cz.cvut.fel.sit.pda.components.BasicAppBar
import cz.cvut.fel.sit.pda.components.GeldsBottomBar
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.ui.theme.DeepPurple500
import cz.cvut.fel.sit.pda.ui.theme.DefaultColor

/**
 * Composable function for displaying a list of transactions grouped by date.
 *
 * @param navController The navigation controller used for navigating between screens.
 * @param transactions The list of transactions to display.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionsScreen(navController: NavHostController, transactions: List<Transaction>) {
    val groupedTransactions = transactions
        .groupBy { it.date }
        .toSortedMap(compareByDescending { it })

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = DefaultColor
    ) {
        Scaffold(
            topBar = {
                BasicAppBar(
                    title = "Transactions",
                    navController = navController,
                    canNavigateBack = false,
                    onNavigateBack = {}
                )
            },
            bottomBar = {
                GeldsBottomBar(navController)
            },
            floatingActionButton = {
                Column(modifier = Modifier.padding(top = 26.dp)) {
                    FloatingActionButton(
                        onClick = { navController.navigate(GeldScreen.AddTransaction.name) },
                        containerColor = DeepPurple500
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Add Transaction")
                    }
                }
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
                            TransactionDateHeader(date, transactionsForDate.sumOf {
                                it.amount
                            })
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

/**
 * Converts a Transaction object to a list of strings containing transaction information.
 *
 * @return A list of strings containing transaction information.
 */
fun Transaction.toInfoList(): List<String> {
    return listOf(
        "Name: $name",
        "Amount: $amount",
        "Type: ${type.displayName}",
        "Date: $date",
        "Card Used: $cardName"
    )
}
