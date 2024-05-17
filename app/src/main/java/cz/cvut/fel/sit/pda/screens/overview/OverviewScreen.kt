package cz.cvut.fel.sit.pda.screens.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.components.BasicAppBar
import cz.cvut.fel.sit.pda.components.GeldsBottomBar
import cz.cvut.fel.sit.pda.database.TransactionType
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.ui.theme.DeepPurple500
import cz.cvut.fel.sit.pda.ui.theme.DefaultColor
import cz.cvut.fel.sit.pda.ui.theme.Indigo50

@Composable
fun OverviewScreen(navController: NavHostController, transactions: List<Transaction>) {
    var isExpensesSelected by remember { mutableStateOf(true) }

    val filteredTransactions = if (isExpensesSelected) {
        transactions.filter { it.type.category == "Expenses" }
    } else {
        transactions.filter { it.type.category == "Income" }
    }

    val groupedTransactions = filteredTransactions
        .groupBy { it.type }
        .toSortedMap(compareBy { it.displayName })

    Scaffold(
        topBar = {
            BasicAppBar(
                title = "Overview",
                navController = navController,
                canNavigateBack = false,
                onNavigateBack = { }
            )
        },
        bottomBar = {
            GeldsBottomBar(navController)
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = DefaultColor
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Button(
                        onClick = { isExpensesSelected = true },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isExpensesSelected) DeepPurple500 else Indigo50,
                            contentColor = if (isExpensesSelected) Indigo50 else DeepPurple500
                        )
                    ) {
                        Text(
                            text = "Expenses",
                            style = MaterialTheme.typography.headlineSmall
                        )

                    }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = { isExpensesSelected = false },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!isExpensesSelected) DeepPurple500 else Indigo50,
                            contentColor = if (!isExpensesSelected) Indigo50 else DeepPurple500
                        )
                    ) {
                        Text(
                            text = "Incomes",
                            style = MaterialTheme.typography.headlineSmall
                        )

                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DefaultColor)
                ) {
                    groupedTransactions.forEach { (transactionType, transactionsForType) ->
                        item {
                            TransactionTypeItem(
                                transactionType,
                                transactionsForType.sumOf { it.amount },
                                onClick = { navController.navigate("categoryTransactions/${transactionType.name}") }
                            )
                        }
                    }
                }
            }
        }
    }
}
