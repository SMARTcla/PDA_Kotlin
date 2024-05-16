package cz.cvut.fel.sit.pda.screens.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.components.BasicAppBar
import cz.cvut.fel.sit.pda.components.GeldsBottomBar
import cz.cvut.fel.sit.pda.database.TransactionType
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.ui.theme.*

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
                            contentColor = if (isExpensesSelected) Indigo50 else Color.Black
                        )
                    ) {
                        Text("Expenses",
                            style = MaterialTheme.typography.headlineSmall)

                    }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = { isExpensesSelected = false },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!isExpensesSelected) DeepPurple500 else BlueGrey850,
                            contentColor = if (!isExpensesSelected) Indigo50 else Indigo100
                        )
                    ) {
                        Text("Incomes",
                            style = MaterialTheme.typography.headlineSmall)

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
                                transactionsForType.sumOf { it.amount })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionTypeItem(transactionType: TransactionType, totalAmount: Long) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(70.dp)
            .background(Grey200, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = transactionType.displayName,
                color = black,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "$totalAmount",
                color = black,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}
