package cz.cvut.fel.sit.pda.screens.transactions

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
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
import cz.cvut.fel.sit.pda.GeldScreen
import cz.cvut.fel.sit.pda.components.BasicAppBar
import cz.cvut.fel.sit.pda.components.GeldsBottomBar
import cz.cvut.fel.sit.pda.database.TransactionType
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.ui.theme.DeepPurple500
import cz.cvut.fel.sit.pda.ui.theme.DefaultColor
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionsScreen(navController: NavHostController, transactions: List<Transaction>) {
    val groupedTransactions = transactions
        .filter { it.type != TransactionType.SALARY }
        .filter { it.type != TransactionType.BENEFITS }
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
                        backgroundColor = DeepPurple500
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

@Composable
fun TransactionDateHeader(date: LocalDate, totalAmount: Long) {
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
                text = date.format(dayOfMonthFormatter),
                style = MaterialTheme.typography.h4,
                color = Color.White
            )
            Column(
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Text(
                    text = date.format(dayOfWeekFormatter),
                    style = MaterialTheme.typography.body2,
                    color = Color.White
                )
                Text(
                    text = date.format(monthYearFormatter),
                    style = MaterialTheme.typography.body2,
                    color = Color.White
                )
            }
        }
        Text(
            text = "$totalAmount CZK",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.align(Alignment.CenterVertically),
            color = Color.White
        )
    }
}



@Composable
fun TransactionDetailScreen(
    navController: NavHostController,
    transaction: Transaction,
    deleteTransaction: () -> Unit
) {
    Scaffold(
        topBar = {
            BasicAppBar(
                title = "Transaction Details",
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
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colors.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                val typography = MaterialTheme.typography
                transaction.toInfoList().forEach { info ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = 2.dp
                    ) {
                        Text(
                            text = info,
                            style = typography.h6,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = {
                        navController.navigate("editTransaction/${transaction.id}")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text("Edit", color = Color.White)
                }
                Button(
                    onClick = {
                        deleteTransaction()
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
                ) {
                    Text("Delete", color = Color.White)
                }
            }
        }
    }
}

private fun Transaction.toInfoList(): List<String> {
    return listOf(
        "Name: $name",
        "Amount: $amount",
        "Type: ${type.displayName}",
        "Date: $date",
        "Card Used: $cardName"
    )
}


@Composable
fun DatePicker(context: Context, currentDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val dateString = remember { mutableStateOf(currentDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))) }
    OutlinedTextField(
        value = dateString.value,
        onValueChange = {},
        label = { Text("Date") },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = {
                showDatePicker(context, currentDate) { newDate ->
                    dateString.value = newDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
                    onDateSelected(newDate)
                }
            }) {
                Icon(Icons.Default.DateRange, contentDescription = "Select Date")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun DropdownMenuEdit(
    selectedType: TransactionType,
    onTypeSelected: (TransactionType) -> Unit,
    transactionTypes: List<TransactionType>
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedTypeName = remember(selectedType) { selectedType.name }

    OutlinedTextField(
        value = selectedTypeName,
        onValueChange = {},
        readOnly = true,
        label = { Text("Transaction Type") },
        trailingIcon = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.fillMaxWidth()
    ) {
        transactionTypes.forEach { type ->
            DropdownMenuItem(
                onClick = {
                    onTypeSelected(type)
                    expanded = false
                }
            ) {
                Text(type.name)
            }
        }
    }
}
