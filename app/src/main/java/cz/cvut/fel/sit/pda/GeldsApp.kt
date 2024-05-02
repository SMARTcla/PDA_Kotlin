package cz.cvut.fel.sit.pda

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.screens.AccountsScreen
import cz.cvut.fel.sit.pda.screens.AddCardScreen
import cz.cvut.fel.sit.pda.screens.AddTransactionScreen
import cz.cvut.fel.sit.pda.screens.BudgetScreen
import cz.cvut.fel.sit.pda.screens.CategoriesScreen
import cz.cvut.fel.sit.pda.screens.OverviewScreen
import cz.cvut.fel.sit.pda.screens.SettingsScreen
import cz.cvut.fel.sit.pda.screens.TransactionsScreen
import cz.cvut.fel.sit.pda.ui.theme.PDATheme

@SuppressLint("ComposableDestinationInComposeScope")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val transactions = remember { mutableStateListOf<Transaction>() }

    NavHost(navController = navController,
        startDestination = GeldScreen.Accounts.name,
        modifier = Modifier) {

        composable(GeldScreen.Accounts.name) {
            AccountsScreen(navController, transactions) }

        composable(GeldScreen.AddCardScreen.name) {
            AddCardScreen(navController) }

        composable(GeldScreen.Overview.name) {
            OverviewScreen(navController) }

        // Passing a list of transactions
        composable(GeldScreen.Transactions.name) {
            TransactionsScreen(navController, transactions) }
        composable(GeldScreen.Budget.name) {
            BudgetScreen(navController) }
        composable(GeldScreen.Categories.name) {
            CategoriesScreen(navController) }

        composable(GeldScreen.Settings.name) {
            SettingsScreen(navController) }

        composable(GeldScreen.AddTransaction.name) {
            AddTransactionScreen(navController) { transaction ->
                transactions.add(transaction)
                navController.popBackStack()
            }
        }
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
