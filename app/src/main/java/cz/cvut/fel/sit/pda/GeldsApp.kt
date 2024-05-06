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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.screens.AccountsScreen
import cz.cvut.fel.sit.pda.screens.AddCardScreen
import cz.cvut.fel.sit.pda.screens.AddTransactionScreen
import cz.cvut.fel.sit.pda.screens.budget.BudgetScreen
import cz.cvut.fel.sit.pda.screens.CategoriesScreen
import cz.cvut.fel.sit.pda.screens.EditTransactionScreen
import cz.cvut.fel.sit.pda.screens.NotificationsScreen
import cz.cvut.fel.sit.pda.screens.OverviewScreen
import cz.cvut.fel.sit.pda.screens.SettingsScreen
import cz.cvut.fel.sit.pda.screens.TransactionDetailScreen
import cz.cvut.fel.sit.pda.screens.TransactionsScreen
import cz.cvut.fel.sit.pda.ui.theme.PDATheme

@SuppressLint("ComposableDestinationInComposeScope")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val transactions = remember { mutableStateListOf<Transaction>() }
    val context = LocalContext.current
    val notificationEnabled = remember { mutableStateOf(getNotificationEnabled(context)) }

    NavHost(navController = navController,
        startDestination = GeldScreen.Accounts.name,
        modifier = Modifier) {

        composable(GeldScreen.Accounts.name) {
            AccountsScreen(navController, transactions) }

        composable(GeldScreen.AddCardScreen.name) {
            AddCardScreen(navController) }

        composable(GeldScreen.Overview.name) {
            OverviewScreen(navController, transactions) }

        composable(GeldScreen.Transactions.name) {
            TransactionsScreen(navController, transactions) }
        composable(GeldScreen.Budget.name) {
            BudgetScreen(navController) }
        composable(GeldScreen.Categories.name) {
            CategoriesScreen(navController) }

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
            AddTransactionScreen(navController) { transaction ->
                transactions.add(transaction)
                navController.popBackStack()
            }
        }
        composable("transactionDetail/{transactionId}") { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getString("transactionId") ?: return@composable
            val transaction = transactions.find { it.id == transactionId }
            if (transaction != null) {
                TransactionDetailScreen(navController, transaction)
            } else {

            }
        }


        composable("editTransaction/{transactionId}") { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getString("transactionId") ?: return@composable
            val transaction = transactions.find { it.id == transactionId }
            if (transaction != null) {
                EditTransactionScreen(navController, transaction, transactions)
            } else {

            }
        }
    }
}

fun getNotificationEnabled(context: Context): Boolean {
    val sharedPref = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
    return sharedPref.getBoolean("NotificationsEnabled", true) // Default is true
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
