package cz.cvut.fel.sit.pda

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.screens.AddTransactionScreen
import cz.cvut.fel.sit.pda.screens.AccountsScreen
import cz.cvut.fel.sit.pda.ui.theme.PDATheme
import cz.cvut.fel.sit.pda.screens.TransactionsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PDATheme {
                AppNavigation()
            }
        }
    }
}



@Composable
fun AppBottomNavigation(navController: NavHostController) {
    BottomNavigation(
        backgroundColor = Color(0xFF69789A),
        contentColor = Color.White
    )
    {
        val currentRoute = currentRoute(navController)
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.AccountBox, contentDescription = "Accounts") },
            label = { Text("Accounts") },
            selected = currentRoute == "accounts",
            onClick = { navigateToScreen(navController, "accounts") },
            selectedContentColor = Color.Black,
            unselectedContentColor = Color.White.copy(0.7f),
            alwaysShowLabel = true
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Overview") },
            label = { Text("Overview") },
            selected = currentRoute == "overview",
            onClick = { navigateToScreen(navController, "overview") },
            selectedContentColor = Color.Black,
            unselectedContentColor = Color.White.copy(0.7f),
            alwaysShowLabel = true
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Transactions") },
            label = { Text("Transactions") },
            selected = currentRoute == "transactions",
            onClick = { navigateToScreen(navController, "transactions") },
            selectedContentColor = Color.Black,
            unselectedContentColor = Color.White.copy(0.7f),
            alwaysShowLabel = true
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Budget") },
            label = { Text("Budget") },
            selected = currentRoute == "budget",
            onClick = { navigateToScreen(navController, "budget") },
            selectedContentColor = Color.Black,
            unselectedContentColor = Color.White.copy(0.7f),
            alwaysShowLabel = true
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Categories") },
            label = { Text("Categories") },
            selected = currentRoute == "categories",
            onClick = { navigateToScreen(navController, "categories") },
            selectedContentColor = Color.Black,
            unselectedContentColor = Color.White.copy(0.7f),
            alwaysShowLabel = true
        )
    }
}

@Composable
fun OverviewScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF586481)) {
        Text(text = "Hello Overview", modifier = Modifier.fillMaxSize())
    }
}
@Composable
fun BudgetScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF586481)) {
        Text(text = "Hello Budget", modifier = Modifier.fillMaxSize())
    }
}
@Composable
fun CategoriesScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF586481)) {
        Text(text = "Hello Categories", modifier = Modifier.fillMaxSize())
    }
}
@Composable
fun GreetingScreen(text: String) {
    Text(text = text, modifier = Modifier.fillMaxSize())
}
fun navigateToScreen(navController: NavHostController, route: String) {
    navController.navigate(route) {
        launchSingleTop = true
    }
}

fun currentRoute(navController: NavHostController): String? {
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    Log.d("Navigation", "Current pass: $currentRoute")
    return currentRoute
}

