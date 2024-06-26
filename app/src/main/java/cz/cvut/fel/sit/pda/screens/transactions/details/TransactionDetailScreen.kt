package cz.cvut.fel.sit.pda.screens.transactions.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.GeldScreen
import cz.cvut.fel.sit.pda.components.BasicAppBar
import cz.cvut.fel.sit.pda.components.GeldsBottomBar
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.screens.transactions.toInfoList
import cz.cvut.fel.sit.pda.ui.theme.Blue800
import cz.cvut.fel.sit.pda.ui.theme.DefaultColor
import cz.cvut.fel.sit.pda.ui.theme.Indigo50
import cz.cvut.fel.sit.pda.ui.theme.Purple800

/**
 * Composable function for displaying transaction details screen.
 *
 * @param navController The navigation controller used for navigating between screens.
 * @param transaction The transaction to display details of.
 * @param deleteTransaction Callback function to delete the transaction.
 */
@Composable
fun TransactionDetailScreen(
    navController: NavHostController,
    transaction: Transaction,
    deleteTransaction: () -> Unit
) {
    Scaffold(
        topBar = {
            BasicAppBar(
                title = "Transaction Details",
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
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                val typography = MaterialTheme.typography
                transaction.toInfoList().forEach { info ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                            .height(50.dp)
                    ) {
                        Text(
                            text = info,
                            style = typography.bodyLarge,
                            modifier = Modifier.padding(
                                horizontal = 6.dp,
                                vertical = 10.dp
                            )
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = {
                        navController.navigate(
                            "${GeldScreen.EditTransaction.name}/${transaction.id}"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple800
                    )
                ) {
                    Text(
                        text = "Edit",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Indigo50
                    )
                }
                Button(
                    onClick = {
                        deleteTransaction()
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Blue800
                    )
                ) {
                    Text(
                        text = "Delete",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Indigo50
                    )
                }
            }
        }
    }
}