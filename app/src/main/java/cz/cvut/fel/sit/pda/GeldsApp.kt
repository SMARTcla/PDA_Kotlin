package cz.cvut.fel.sit.pda

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.screens.AccountsScreen
import cz.cvut.fel.sit.pda.screens.AddTransactionScreen
import cz.cvut.fel.sit.pda.screens.TransactionsScreen
import cz.cvut.fel.sit.pda.ui.theme.PDATheme


enum class GeldScreen() {
    Accounts,
    Overview,
    Transactions,
    Budget,
    Categories,
    AddTransaction,

    // Add new values in future

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val transactions = remember { mutableStateListOf<Transaction>() }
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF586481)) {
        Scaffold(
                bottomBar = { AppBottomNavigation(navController) },
                content = { paddingValues ->
                    NavHost(navController = navController,
                        startDestination = GeldScreen.Accounts.name,
                        modifier = Modifier.padding(paddingValues)) {

                        composable(GeldScreen.Accounts.name) {
                            AccountsScreen(transactions) }
                        composable(GeldScreen.Overview.name) {
                            OverviewScreen() }

                        // Passing a list of transactions
                        composable(GeldScreen.Transactions.name) {
                            TransactionsScreen(navController, transactions) }
                        composable(GeldScreen.Budget.name) {
                            BudgetScreen() }
                        composable(GeldScreen.Categories.name) {
                            CategoriesScreen() }
                        composable(GeldScreen.AddTransaction.name) {
                            AddTransactionScreen(navController) { transaction ->
                                transactions.add(transaction)
                                navController.popBackStack()
                            }
                        }
                    }
                }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewGreetingScreen() {
    PDATheme {
        GreetingScreen("Preview Text")
    }
}
