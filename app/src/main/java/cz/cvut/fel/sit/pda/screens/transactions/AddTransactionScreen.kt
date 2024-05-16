package cz.cvut.fel.sit.pda.screens.transactions

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.components.BasicAppBar
import cz.cvut.fel.sit.pda.components.GeldsBottomBar
import cz.cvut.fel.sit.pda.database.TransactionEntity
import cz.cvut.fel.sit.pda.database.TransactionType
import cz.cvut.fel.sit.pda.models.BankCard
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.screens.transactions.ui.TransactionViewModel
import cz.cvut.fel.sit.pda.ui.theme.DeepPurple500
import cz.cvut.fel.sit.pda.ui.theme.DefaultColor
import cz.cvut.fel.sit.pda.ui.theme.Grey50
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@Composable
fun AddTransactionScreen(
    navController: NavHostController,
    viewModel: TransactionViewModel,
    cards: List<BankCard>,
    saveTransaction: (TransactionEntity) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var isExpensesSelected by remember { mutableStateOf(true) }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            BasicAppBar(
                title = "Add Transaction",
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
            color = DefaultColor
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            isExpensesSelected = true
                            viewModel.updateType(TransactionType.RESTAURANT)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (isExpensesSelected) DeepPurple500 else Color.White,
                            contentColor = if (isExpensesSelected) Color.White else Color.Black
                        )
                    ) {
                        Text("Expenses")
                    }
                    Button(
                        onClick = {
                            isExpensesSelected = false
                            viewModel.updateType(TransactionType.SALARY)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (!isExpensesSelected) DeepPurple500 else Color.White,
                            contentColor = if (!isExpensesSelected) Color.White else Color.Black
                        )
                    ) {
                        Text("Income")
                    }
                }
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = viewModel::updateName,
                    label = { Text("Name", color = Color.White) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        cursorColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = uiState.amount,
                    onValueChange = viewModel::updateAmount,
                    label = {
                        Text("Amount", color = Color.White)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        cursorColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = selectedDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
                    onValueChange = {},
                    label = { Text("Date", color = Color.White) },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            showDatePicker(context, selectedDate) { date ->
                                selectedDate = date
                            }
                        }) {
                            Icon(
                                Icons.Default.DateRange, contentDescription = "Select Date",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        disabledTextColor = Color.White,
                        cursorColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    selectedType = uiState.type,
                    onTypeSelected = viewModel::updateType,
                    transactionTypes = if (isExpensesSelected) {
                        TransactionType.entries.filter { it.category == "Expenses" }
                    } else {
                        TransactionType.entries.filter { it.category == "Income" }
                    }
                )
                CardDropdownMenu(
                    selectedCard = uiState.cardName,
                    onCardSelected = viewModel::updateCardName,
                    bankCards = cards
                )
                Button(
                    onClick = {
                        saveTransaction(viewModel.getTransaction())
                        /*if (uiState.name.isNotEmpty() && uiState.amount.isNotEmpty()) {
                            try {
                                val transactionAmount = uiState.amount.toDouble()
                                addTransaction(
                                    Transaction(
                                        name = uiState.name,
                                        amount = transactionAmount,
                                        type = uiState.type,
                                        date = selectedDate,
                                        category = uiState.type.category,
                                        cardName = uiState.card
                                    )
                                )
                                navController.popBackStack()
                            } catch (e: NumberFormatException) {
                                Log.e("AddTransactionScreen", "Error parsing amount", e)
                            }
                        }*/
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = DeepPurple500)
                ) {
                    Text("Add Transaction", color = Grey50)
                }
            }
        }
    }
}



@Composable
fun DropdownMenu(
    selectedType: TransactionType,
    onTypeSelected: (TransactionType) -> Unit,
    transactionTypes: List<TransactionType>
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clickable { expanded = true }
                .background(Color.White, shape = RoundedCornerShape(4.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = selectedType.displayName,
                color = Color.Black
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(),
            content = {
                Column(
                    modifier = Modifier.background(Color.White, shape = RoundedCornerShape(4.dp))
                ) {
                    transactionTypes.forEach { type ->
                        DropdownMenuItem(
                            onClick = {
                                onTypeSelected(type)
                                expanded = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(type.displayName, color = Color.Black)
                        }
                    }
                }
            }
        )
    }
}




@Composable
fun CardDropdownMenu(selectedCard: String, onCardSelected: (String) -> Unit, bankCards: List<BankCard>) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clickable { expanded = true }
                .background(Color.White, shape = RoundedCornerShape(4.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = selectedCard,
                color = Color.Black
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(),
            content = {
                Column(
                    modifier = Modifier.background(Color.White, shape = RoundedCornerShape(4.dp))
                ) {
                    bankCards.forEach { card ->
                        DropdownMenuItem(
                            onClick = {
                                onCardSelected(card.name)
                                expanded = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(card.name, color = Color.Black)
                        }
                    }
                }
            }
        )
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