package cz.cvut.fel.sit.pda.screens.transactions.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import cz.cvut.fel.sit.pda.screens.transactions.CardDropdownMenu
import cz.cvut.fel.sit.pda.screens.transactions.DropdownMenu
import cz.cvut.fel.sit.pda.screens.transactions.showDatePicker
import cz.cvut.fel.sit.pda.screens.transactions.ui.TransactionViewModel
import cz.cvut.fel.sit.pda.ui.theme.DeepPurple500
import cz.cvut.fel.sit.pda.ui.theme.DefaultColor
import cz.cvut.fel.sit.pda.ui.theme.Indigo50
import java.time.format.DateTimeFormatter

/**
 * Composable function for editing a transaction.
 *
 * @param navController The navigation controller used for navigating between screens.
 * @param viewModel The view model providing data and logic for the edit screen.
 * @param banks The list of banks for displaying card options.
 * @param updateTransaction Callback function to update the transaction after editing.
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditTransactionScreen(
    navController: NavHostController,
    viewModel: TransactionViewModel,
    banks: List<BankEntity>,
    updateTransaction: (TransactionEntity) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var isExpensesSelected by remember {
        mutableStateOf(uiState.type.category == "Expenses")
    }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            BasicAppBar(
                title = "Edit Transaction",
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
                    label = { Text("Amount", color = Color.White) },
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
                    value = uiState.date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
                    onValueChange = {},
                    label = { Text("Date", color = Color.White) },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            showDatePicker(context, uiState.date, viewModel::updateDate)
                        }) {
                            Icon(
                                Icons.Default.DateRange,
                                contentDescription = "Select Date",
                                tint = Color.White
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
                    bankCards = banks
                )
                Button(
                    onClick = {
                        updateTransaction(viewModel.getTransaction())
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = DeepPurple500)
                ) {
                    Text("Done")
                }
            }
        }
    }
}