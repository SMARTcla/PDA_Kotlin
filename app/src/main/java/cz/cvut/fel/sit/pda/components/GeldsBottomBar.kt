package cz.cvut.fel.sit.pda.components

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cz.cvut.fel.sit.pda.GeldScreen
import cz.cvut.fel.sit.pda.R
import cz.cvut.fel.sit.pda.ui.theme.PDATheme

@Composable
fun GeldsBottomBar(navController: NavHostController) {
    val items = listOf(
        Triple(R.drawable.accounts, stringResource(R.string.accounts_tab_name), GeldScreen.Accounts.name),
        Triple(R.drawable.overview, stringResource(R.string.overview_tab_name), GeldScreen.Overview.name),
        Triple(R.drawable.transactions, stringResource(R.string.transactions_tab_name), GeldScreen.Transactions.name),
        Triple(R.drawable.budget, stringResource(R.string.budget_tab_name), GeldScreen.Budget.name),
        Triple(R.drawable.categories, stringResource(R.string.categories_tab_name), GeldScreen.Categories.name),

        )

    NavigationBar {
        val currentRoute = navController.currentDestination?.route

        items.forEach {
            NavigationBarItem(
                icon = { Icon(painter = painterResource(id = it.first), contentDescription = it.second) },
                label = { Text(
                            it.second,
                            // changed size of Icons name
                            style = TextStyle(fontSize = 11.sp)
                    )
                },
                selected = currentRoute == it.third,
                onClick = {
                    navController.navigate(it.third) {
                        // Avoid multiple copies of the same destination when reselecting the same item
                        launchSingleTop = true
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true // Save the state of all destinations before popping them
                        }
                        // Restore state when navigating to a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewGeldsBottomBar() {
    PDATheme {
        Scaffold(
            bottomBar = {
                GeldsBottomBar(navController = rememberNavController())
            }
        ) {
        }
    }
}
