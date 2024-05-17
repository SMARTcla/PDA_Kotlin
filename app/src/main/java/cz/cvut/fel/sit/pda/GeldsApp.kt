package cz.cvut.fel.sit.pda

import android.annotation.SuppressLint
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
import cz.cvut.fel.sit.pda.database.TransactionType
import cz.cvut.fel.sit.pda.screens.accounts.AccountsScreen
import cz.cvut.fel.sit.pda.screens.accounts.AddCardScreen
import cz.cvut.fel.sit.pda.screens.accounts.CardDetailScreen
import cz.cvut.fel.sit.pda.screens.accounts.EditCardScreen
import cz.cvut.fel.sit.pda.screens.category.CategoriesScreen
import cz.cvut.fel.sit.pda.screens.category.CategoryTransactionsScreen
import cz.cvut.fel.sit.pda.screens.overview.OverviewScreen
import cz.cvut.fel.sit.pda.screens.settings.SettingsScreen
import cz.cvut.fel.sit.pda.screens.transactions.add.AddTransactionScreen
import cz.cvut.fel.sit.pda.screens.transactions.details.EditTransactionScreen
import cz.cvut.fel.sit.pda.screens.transactions.details.TransactionDetailScreen
import cz.cvut.fel.sit.pda.screens.transactions.ui.TransactionScreenUiState
import cz.cvut.fel.sit.pda.screens.transactions.TransactionsScreen
import cz.cvut.fel.sit.pda.screens.transactions.ui.TransactionViewModel
import cz.cvut.fel.sit.pda.screens.transactions.ui.TransactionViewModelFactory
import kotlinx.coroutines.launch

@SuppressLint("ComposableDestinationInComposeScope")
@Composable
fun AppNavigation(
    navController: NavHostController,
    geldViewModel: GeldViewModel,
) {
    val appUiState by geldViewModel.uiState.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = GeldScreen.Accounts.name,
        modifier = Modifier
    ) {

        composable(GeldScreen.Accounts.name) {
            AccountsScreen(
                navController,
                appUiState.transactions,
                appUiState.banks
            )
        }

        composable(GeldScreen.AddCardScreen.name) {
            val coroutineScope = rememberCoroutineScope()

            AddCardScreen(
                navController = navController,
                cards = appUiState.banks,
                saveBank = { bank ->
                    coroutineScope.launch {
                        geldViewModel.saveBank(bank)
                    }
                }
            )
        }

        composable(GeldScreen.Overview.name) {
            OverviewScreen(navController, appUiState.transactions)
        }

        composable(GeldScreen.Transactions.name) {
            TransactionsScreen(navController, appUiState.transactions)
        }

        composable(GeldScreen.Categories.name) {
            CategoriesScreen(navController, appUiState.transactions)
        }

        composable(GeldScreen.Settings.name) {
            SettingsScreen(navController)
        }

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
                cards = appUiState.banks
            )
        }

        composable(route = "transactionDetail/{transactionId}") { backStackEntry ->
            val transactionId =
                backStackEntry.arguments?.getString("transactionId") ?: return@composable
            val transaction = appUiState.transactions.find { it.id == transactionId }
            val coroutineScope = rememberCoroutineScope()

            if (transaction != null) {
                TransactionDetailScreen(navController, transaction) {
                    coroutineScope.launch {
                        geldViewModel.deleteTransaction(transaction)
                    }
                }
            }
        }

        composable(route = "cardDetails/{cardName}") { backStackEntry ->
            val coroutineScope = rememberCoroutineScope()
            backStackEntry.arguments?.getString("cardName")?.let { cardName ->
                val bank = appUiState.banks.find { it.name == cardName }
                if (bank != null) {
                    CardDetailScreen(navController, bank) { bankToDelete ->
                        coroutineScope.launch {
                            geldViewModel.deleteBank(bankToDelete)
                        }
                    }
                }
            }
        }

        composable(route = "editTransaction/{transactionId}") { backStackEntry ->
            val transactionId =
                backStackEntry.arguments?.getString("transactionId") ?: return@composable
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
                    banks = appUiState.banks,
                    updateTransaction = {
                        coroutineScope.launch {
                            geldViewModel.updateTransaction(it)
                        }
                    }
                )
            }

        }

        composable(route = "editCard/{cardName}") { backStackEntry ->
            val cardName = backStackEntry.arguments?.getString("cardName")
            val bank = appUiState.banks.find { it.name == cardName }
            val coroutineScope = rememberCoroutineScope()

            if (bank != null) {
                EditCardScreen(navController, bank, onUpdate = { updatedBank ->
                    coroutineScope.launch {
                        geldViewModel.updateBank(updatedBank)
                    }
                })
            }
        }

        composable("categoryTransactions/{category}") { backStackEntry ->
            val categoryString = backStackEntry.arguments?.getString("category") ?: return@composable
            val category = TransactionType.valueOf(categoryString)
            CategoryTransactionsScreen(
                navController = navController,
                transactions = appUiState.transactions,
                category = category
            )
        }

        composable("categoryTransactions/{category}") { backStackEntry ->
            val categoryString = backStackEntry.arguments?.getString("category") ?: return@composable
            val category = TransactionType.valueOf(categoryString)
            CategoryTransactionsScreen(
                navController = navController,
                transactions = appUiState.transactions,
                category = category
            )
        }


    }
}
