package cz.cvut.fel.sit.pda.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.components.TransactionItem
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.models.TransactionType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionsScreen(navController: NavHostController, transactions: MutableList<Transaction>) {
    val groupedTransactions = transactions.groupBy { it.date }
        .toSortedMap(compareByDescending { it })
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF586481)
    ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate("add_transaction") },
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Transaction")
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
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

@Composable
fun TransactionDateHeader(date: LocalDate, totalAmount: Double) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFF69789A))
    ) {
        Column {
            Text(
                text = date.dayOfMonth.toString(),
                style = MaterialTheme.typography.h4,
                // Дополнительные стилистики по желанию
            )
            Text(
                text = date.format(dateFormatter),
                style = MaterialTheme.typography.subtitle1,
                // Дополнительные стилистики по желанию
            )
            Text(
                text = "$totalAmount Kč",
                style = MaterialTheme.typography.subtitle1,
                // Дополнительные стилистики по желанию
            )
        }
    }
}