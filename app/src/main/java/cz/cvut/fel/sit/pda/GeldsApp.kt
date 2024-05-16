package cz.cvut.fel.sit.pda

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cz.cvut.fel.sit.pda.database.GeldViewModel
import cz.cvut.fel.sit.pda.models.BankCard
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.screens.accounts.AccountsScreen
import cz.cvut.fel.sit.pda.screens.accounts.AddCardScreen
import cz.cvut.fel.sit.pda.screens.accounts.CardDetailScreen
import cz.cvut.fel.sit.pda.screens.accounts.EditCardScreen
import cz.cvut.fel.sit.pda.screens.accounts.deleteCard
import cz.cvut.fel.sit.pda.screens.category.CategoriesScreen
import cz.cvut.fel.sit.pda.screens.overview.OverviewScreen
import cz.cvut.fel.sit.pda.screens.settings.SettingsScreen
import cz.cvut.fel.sit.pda.screens.transactions.AddTransactionScreen
import cz.cvut.fel.sit.pda.screens.transactions.EditTransactionScreen
import cz.cvut.fel.sit.pda.screens.transactions.TransactionDetailScreen
import cz.cvut.fel.sit.pda.screens.transactions.TransactionScreenUiState
import cz.cvut.fel.sit.pda.screens.transactions.TransactionsScreen
import cz.cvut.fel.sit.pda.screens.transactions.ui.TransactionViewModel
import cz.cvut.fel.sit.pda.screens.transactions.ui.TransactionViewModelFactory
import kotlinx.coroutines.launch

@SuppressLint("ComposableDestinationInComposeScope")
@Composable
fun AppNavigation(
    navController: NavHostController,
    geldViewModel: GeldViewModel,
//    onEvent: (GeldEvent) -> Boolean
) {
    val appUiState by geldViewModel.uiState.collectAsStateWithLifecycle()
//    val notificationEnabled = remember { mutableStateOf(getNotificationEnabled(context)) }

    NavHost(navController = navController,
        startDestination = GeldScreen.Accounts.name,
        modifier = Modifier
    ) {

        composable(GeldScreen.Accounts.name) {
            AccountsScreen(
                navController,
                appUiState.transactions,
                appUiState.cards
            )
        }

        composable(GeldScreen.AddCardScreen.name) {
            val coroutineScope = rememberCoroutineScope()

            AddCardScreen(
                navController = navController,
                cards = appUiState.cards,
                saveBank = { bank ->
                    coroutineScope.launch {
                        geldViewModel.saveBank(bank)
                    }
                }
            )
        }

        composable(GeldScreen.Overview.name) {
            OverviewScreen(navController, appUiState.transactions) }

        composable(GeldScreen.Transactions.name) {
            TransactionsScreen(navController, appUiState.transactions)
        }

        composable(GeldScreen.Categories.name) {
            CategoriesScreen(navController, appUiState.transactions) }

        composable(GeldScreen.Settings.name) {
            SettingsScreen(navController) }

//        composable(GeldScreen.Notifications.name) {
//            NotificationsScreen(navController, notificationEnabled.value) { enabled ->
//                notificationEnabled.value = enabled
//                val editor = context.getSharedPreferences("AppSettings",
//                    Context.MODE_PRIVATE).edit()
//                editor.putBoolean("NotificationsEnabled", enabled)
//                editor.apply()
//            }
//        }

        composable(GeldScreen.AddTransaction.name) {
            val viewModel = viewModel<TransactionViewModel>()
            val coroutineScope = rememberCoroutineScope()

            AddTransactionScreen(
                navController = navController,
                viewModel = viewModel,
                saveTransaction = { transaction ->
                    coroutineScope.launch {
                        geldViewModel.saveTransaction(transaction)
                    }
                    navController.popBackStack()
                },
                cards = appUiState.banks.map { BankCard(it.name) }
            )
        }

        composable("transactionDetail/{transactionId}") { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getString("transactionId") ?: return@composable
            val transaction = appUiState.transactions.find { it.id == transactionId }

            val coroutineScope = rememberCoroutineScope()

            if (transaction != null) {
                TransactionDetailScreen(navController, transaction) {
                    coroutineScope.launch {
                        geldViewModel.deleteTransaction(transaction)
                    }
                }
            } else {

            }
        }
        composable("cardDetails/{cardName}") { backStackEntry ->
            backStackEntry.arguments?.getString("cardName")?.let { cardName ->
                val card = appUiState.cards.find { it.name == cardName }
                if (card != null) {
                    CardDetailScreen(navController, card) { cardToDelete ->
                        deleteCard(cardToDelete, appUiState.cards.toMutableList())
                    }
                } else {

                }
            }
        }


        composable("editTransaction/{transactionId}") { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getString("transactionId") ?: return@composable
            val transaction = appUiState.transactions.find { it.id == transactionId }
            val coroutineScope = rememberCoroutineScope()

            val viewModel = viewModel<TransactionViewModel>(
                factory = transaction?.let {
                    TransactionViewModelFactory(
                        TransactionScreenUiState(
                            name = transaction.name,
                            amount = transaction.amount.toString(),
                            type = transaction.type,
                            date = transaction.date,
                            cardName = transaction.cardName,
                            transactionId = transaction.id.toLong()
                        )
                    )
                }
            )

            if (transaction != null) {
                EditTransactionScreen(
                    navController = navController,
                    viewModel = viewModel,
                    banks = appUiState.cards,
                    updateTransaction = {
                        coroutineScope.launch {
                            geldViewModel.updateTransaction(it)
                        }
                    }
                )
            } else {

            }
        }

//        composable("editCard/{cardName}") { backStackEntry ->
//            val cardName = backStackEntry.arguments?.getString("cardName") ?: return@composable
//            val card = appUiState.cards.find { it.name == cardName }
//            if (card != null) {
//                EditCardScreen(navController, card, onUpdate = { updatedCard ->
//                    val index = appUiState.cards.indexOfFirst { it.name == card.name }
//                    /*if (index != -1) {
//                        appUiState.cards.get(index) = updatedCard
//                    }*/
//                })
//            } else {
//            }
//        }
    }
}


fun MutableList<Transaction>.updateTransaction(updatedTransaction: Transaction) {
    val index = this.indexOfFirst { it.id == updatedTransaction.id }
    if (index != -1) {
        this[index] = updatedTransaction
    }
}

