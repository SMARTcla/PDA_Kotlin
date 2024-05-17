package cz.cvut.fel.sit.pda.screens.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import cz.cvut.fel.sit.pda.database.BankEntity
import cz.cvut.fel.sit.pda.database.TransactionEntity
import cz.cvut.fel.sit.pda.database.TransactionType
import cz.cvut.fel.sit.pda.screens.transactions.ui.TransactionViewModel
import cz.cvut.fel.sit.pda.ui.theme.DeepPurple500
import cz.cvut.fel.sit.pda.ui.theme.DefaultColor
import cz.cvut.fel.sit.pda.ui.theme.Grey50
import cz.cvut.fel.sit.pda.ui.theme.Indigo50
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun AddTransactionScreen(
    navController: NavHostController,
    viewModel: TransactionViewModel,
    cards: List<BankEntity>,
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
                            backgroundColor = if (isExpensesSelected) DeepPurple500 else Indigo50,
                            contentColor = if (isExpensesSelected) Indigo50 else Color.Black
                        )
                    ) {
                        Text(
                            text = "Expenses",
                            style = MaterialTheme.typography.headlineSmall
                        )
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
                        Text(
                            text = "Income",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = viewModel::updateName,
                    label = {
                        Text("Name", color = Indigo50)
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Indigo50,
                        cursorColor = Indigo50,
                        focusedBorderColor = Indigo50,
                        unfocusedBorderColor = Indigo50,
                        focusedLabelColor = Indigo50,
                        unfocusedLabelColor = Indigo50
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = uiState.amount,
                    onValueChange = viewModel::updateAmount,
                    label = {
                        Text("Amount", color = Indigo50)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Indigo50,
                        cursorColor = Indigo50,
                        focusedBorderColor = Indigo50,
                        unfocusedBorderColor = Indigo50,
                        focusedLabelColor = Indigo50,
                        unfocusedLabelColor = Indigo50
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
                                tint = Indigo50
                            )
                        }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Indigo50,
                        disabledTextColor = Indigo50,
                        cursorColor = Indigo50,
                        focusedBorderColor = Indigo50,
                        unfocusedBorderColor = Indigo50,
                        focusedLabelColor = Indigo50,
                        unfocusedLabelColor = Indigo50
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
                    },
                    modifier = Modifier
                        //
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = DeepPurple500)
                ) {
                    Text(
                        text = "Add Transaction",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Grey50
                    )
                }
            }
        }
    }
}


