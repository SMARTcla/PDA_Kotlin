package cz.cvut.fel.sit.pda.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.models.TransactionType

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OverviewScreen(navController: NavHostController, transactions: MutableList<Transaction>) {
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
                canNavigateBack = true,
                onNavigateBack = { navController.popBackStack() }
            )
        },
        bottomBar = {
            GeldsBottomBar(navController)
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF586481)
        ) {
            Column(modifier = Modifier.padding(innerPadding)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Button(
                        onClick = { isExpensesSelected = true },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (isExpensesSelected) MaterialTheme.colors.primary else Color.White,
                            contentColor = if (isExpensesSelected) Color.White else Color.Black
                        )
                    ) {
                        Text("Expenses")
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = { isExpensesSelected = false },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (!isExpensesSelected) MaterialTheme.colors.primary else Color.White,
                            contentColor = if (!isExpensesSelected) Color.White else Color.Black
                        )
                    ) {
                        Text("Income")
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF586481))
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
fun TransactionTypeItem(transactionType: TransactionType, totalAmount: Double) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(70.dp)
            .background(Color(0xFF334455), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = transactionType.displayName,
                color = Color.White,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "$totalAmount",
                color = Color.White,
                style = MaterialTheme.typography.h5
            )
        }
    }
}
