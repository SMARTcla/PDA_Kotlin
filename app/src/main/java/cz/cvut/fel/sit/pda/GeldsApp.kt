package cz.cvut.fel.sit.pda

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.cvut.fel.sit.pda.models.BankCard
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.screens.accounts.AccountsScreen
import cz.cvut.fel.sit.pda.screens.accounts.AddCardScreen
import cz.cvut.fel.sit.pda.screens.transactions.AddTransactionScreen
import cz.cvut.fel.sit.pda.screens.accounts.CardDetailScreen
import cz.cvut.fel.sit.pda.screens.accounts.EditCardScreen
import cz.cvut.fel.sit.pda.screens.budget.category.CategoriesScreen
import cz.cvut.fel.sit.pda.screens.transactions.EditTransactionScreen
import cz.cvut.fel.sit.pda.screens.notifications.NotificationsScreen
import cz.cvut.fel.sit.pda.screens.overview.OverviewScreen
import cz.cvut.fel.sit.pda.screens.settings.SettingsScreen
import cz.cvut.fel.sit.pda.screens.transactions.TransactionDetailScreen
import cz.cvut.fel.sit.pda.screens.transactions.TransactionsScreen
import cz.cvut.fel.sit.pda.screens.accounts.deleteCard
import cz.cvut.fel.sit.pda.screens.transactions.deleteTransaction
import cz.cvut.fel.sit.pda.ui.theme.PDATheme

@SuppressLint("ComposableDestinationInComposeScope")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val transactions = remember { mutableStateListOf<Transaction>() }
    val cards = remember { mutableStateListOf<BankCard>() }
    val context = LocalContext.current
    val notificationEnabled = remember { mutableStateOf(getNotificationEnabled(context)) }

    NavHost(navController = navController,
        startDestination = GeldScreen.Accounts.name,
        modifier = Modifier) {

        composable(GeldScreen.Accounts.name) {
            AccountsScreen(navController, transactions, cards) }

        composable(GeldScreen.AddCardScreen.name) {
            AddCardScreen(navController, cards) }

        composable(GeldScreen.Overview.name) {
            OverviewScreen(navController, transactions) }

        composable(GeldScreen.Transactions.name) {
            TransactionsScreen(navController, transactions) }
        composable(GeldScreen.Categories.name) {
            CategoriesScreen(navController, transactions) }

        composable(GeldScreen.Settings.name) {
            SettingsScreen(navController) }

        composable(GeldScreen.Notifications.name) {
            NotificationsScreen(navController, notificationEnabled.value) { enabled ->
                notificationEnabled.value = enabled
                val editor = context.getSharedPreferences("AppSettings",
                    Context.MODE_PRIVATE).edit()
                editor.putBoolean("NotificationsEnabled", enabled)
                editor.apply()
            }
        }

        composable(GeldScreen.AddTransaction.name) {
            AddTransactionScreen(navController, { transaction ->
                transactions.add(transaction)
                navController.popBackStack()
            }, cards)
        }

        composable("transactionDetail/{transactionId}") { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getString("transactionId") ?: return@composable
            val transaction = transactions.find { it.id == transactionId }
            if (transaction != null) {
                TransactionDetailScreen(navController, transaction, transactions, { transactionToDelete ->
                    deleteTransaction(transactionToDelete, transactions)
                })
            } else {

            }
        }
        composable("cardDetails/{cardName}") { backStackEntry ->
            backStackEntry.arguments?.getString("cardName")?.let { cardName ->
                val card = cards.find { it.name == cardName }
                if (card != null) {
                    CardDetailScreen(navController, card, { cardToDelete ->
                        deleteCard(cardToDelete, cards)
                    })
                } else {

                }
            }
        }


        composable("editTransaction/{transactionId}") { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getString("transactionId") ?: return@composable
            val transaction = transactions.find { it.id == transactionId }
            if (transaction != null) {
                EditTransactionScreen(navController, transaction, transactions, cards)
            } else {

            }
        }

        composable("editCard/{cardName}") { backStackEntry ->
            val cardName = backStackEntry.arguments?.getString("cardName") ?: return@composable
            val card = cards.find { it.name == cardName }
            if (card != null) {
                EditCardScreen(navController, card, onUpdate = { updatedCard ->
                    val index = cards.indexOfFirst { it.name == card.name }
                    if (index != -1) {
                        cards[index] = updatedCard
                    }
                })
            } else {
            }
        }
    }
}

fun getNotificationEnabled(context: Context): Boolean {
    val sharedPref = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
    return sharedPref.getBoolean("NotificationsEnabled", true)
}


fun MutableList<Transaction>.updateTransaction(updatedTransaction: Transaction) {
    val index = this.indexOfFirst { it.id == updatedTransaction.id }
    if (index != -1) {
        this[index] = updatedTransaction
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewGreetingScreen() {
    PDATheme {
        AppNavigation()
    }
}
