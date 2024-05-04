package cz.cvut.fel.sit.pda.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionsScreen(navController: NavHostController, transactions: MutableList<Transaction>) {
    val groupedTransactions = transactions
        .filter { it.type != TransactionType.SALARY }
        .filter { it.type != TransactionType.BENEFITS }
        .groupBy { it.date }
        .toSortedMap(compareByDescending { it })

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
            floatingActionButton = {
                Column(modifier = Modifier.padding(top = 26.dp)) {
                    FloatingActionButton(
                        onClick = { navController.navigate(GeldScreen.AddTransaction.name) },
                        backgroundColor = MaterialTheme.colors.primary
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Add Transaction")
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = false,
            content = { padding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(Color(0xFF586481))
                ) {
                    groupedTransactions.forEach { (date, transactionsForDate) ->
                        stickyHeader {
                            TransactionDateHeader(date, transactionsForDate.sumOf { it.amount })
                        }
                        items(transactionsForDate) { transaction ->
                            TransactionItem(transaction)
                        }
                    }
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionDateHeader(date: LocalDate, totalAmount: Double) {
    val dayOfMonthFormatter = DateTimeFormatter.ofPattern("dd")
    val monthYearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
    val dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFF69789A)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = date.format(dayOfMonthFormatter), // День месяца
                style = MaterialTheme.typography.h4,
                color = Color.White
            )
            Column(
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Text(
                    text = date.format(dayOfWeekFormatter), // День недели
                    style = MaterialTheme.typography.body2,
                    color = Color.White
                )
                Text(
                    text = date.format(monthYearFormatter), // Месяц и год
                    style = MaterialTheme.typography.body2,
                    color = Color.White
                )
            }
        }
        Text(
            text = "${totalAmount} CZK",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.align(Alignment.CenterVertically),
            color = Color.White
        )
    }
}
