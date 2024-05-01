package cz.cvut.fel.sit.pda.screens

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.models.TransactionType
import cz.cvut.fel.sit.pda.utils.TemporaryDatabase
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.Surface
import cz.cvut.fel.sit.pda.components.BasicAppBar
import cz.cvut.fel.sit.pda.models.BankCard
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTransactionScreen(navController: NavHostController, addTransaction: (Transaction) -> Unit) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(TransactionType.RESTAURANT) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val context = LocalContext.current
    var selectedCard by remember { mutableStateOf(TemporaryDatabase.bankCards.first().name) }

    Scaffold(
        topBar = {
            BasicAppBar(
                title = "Add Transaction",
                navController = navController,
                canNavigateBack = true,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
    Surface(
        modifier = Modifier.fillMaxSize().padding(innerPadding),
        color = Color(0xFF586481)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") }
            )
            TextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            // Дата
            val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
            TextField(
                value = selectedDate.format(dateFormatter),
                onValueChange = {},
                label = { Text("Date") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = {
                        // Для простоты используем диалог выбора даты Android
                        showDatePicker(context, selectedDate) { date ->
                            selectedDate = date
                        }
                    }) {
                        Icon(Icons.Default.DateRange, contentDescription = null)
                    }
                }
            )
            DropdownMenu(
                selectedType = selectedType,
                onTypeSelected = { selectedType = it },
                transactionTypes = TransactionType.values().toList()
                    .filter { it.category == "Expenses" } // Фильтр для расходов
            )
            CardDropdownMenu(
                selectedCard = selectedCard,
                onCardSelected = { selectedCard = it },
                bankCards = TemporaryDatabase.bankCards
            )
            Button(
                onClick = {
                    if (name.isNotEmpty() && amount.isNotEmpty()) {
                        try {
                            val transactionAmount = amount.toDouble()
                            addTransaction(
                                Transaction(
                                    name = name,
                                    amount = transactionAmount,
                                    type = selectedType,
                                    date = selectedDate,
                                    category = selectedType.category,
                                    cardName = selectedCard
                                )
                            )
                            navController.popBackStack()
                        } catch (e: NumberFormatException) {
                            Log.e("AddTransactionScreen", "Error parsing amount", e)
                        }
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Add Transaction")
            }
        }
    }
    }
}

@Composable
fun DropdownMenu(selectedType: TransactionType, onTypeSelected:
    (TransactionType) -> Unit, transactionTypes: List<TransactionType>) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Text(selectedType.displayName, modifier = Modifier.clickable { expanded = true })
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            transactionTypes.forEach { type ->
                DropdownMenuItem(onClick = {
                    onTypeSelected(type)
                    expanded = false
                }) {
                    Text(type.displayName)
                }
            }
        }
    }
}
@Composable
fun CardDropdownMenu(selectedCard: String, onCardSelected: (String) -> Unit, bankCards: List<BankCard>) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Text(selectedCard, modifier = Modifier.clickable { expanded = true })
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            bankCards.forEach { card ->
                DropdownMenuItem(onClick = {
                    onCardSelected(card.name)
                    expanded = false
                }) {
                    Text(card.name)
                }
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun showDatePicker(context: Context, currentDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val calendar = Calendar.getInstance().apply {
        set(currentDate.year, currentDate.monthValue - 1, currentDate.dayOfMonth)
    }
    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onDateSelected(LocalDate.of(year, month + 1, dayOfMonth))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}