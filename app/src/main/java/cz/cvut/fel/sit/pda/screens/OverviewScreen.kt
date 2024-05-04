package cz.cvut.fel.sit.pda.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.GeldScreen
import cz.cvut.fel.sit.pda.components.BasicAppBar
import cz.cvut.fel.sit.pda.components.GeldsBottomBar
import cz.cvut.fel.sit.pda.components.TransactionItem
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.models.TransactionType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(navController: NavHostController, transactions: MutableList<Transaction>) {
    val groupedTransactions = transactions
        .filter { it.type != TransactionType.SALARY }
        .filter { it.type != TransactionType.BENEFITS }
        .groupBy { it.type }
        .toSortedMap(compareBy { it.displayName })

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF586481)
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
            content = { padding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(Color(0xFF586481))
                ) {
                    groupedTransactions.forEach { (transactionType, transactionsForType) ->
                        item {
                            TransactionTypeItem(transactionType, transactionsForType.sumByDouble { it.amount })
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun TransactionTypeItem(transactionType: TransactionType, totalAmount: Double) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(140.dp)
            .background(Color(0xFF334455), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Text(
                text = transactionType.displayName,
                color = Color.White,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Total amount: $totalAmount CZK",
                color = Color.White,
                style = MaterialTheme.typography.body1
            )
        }
    }
}