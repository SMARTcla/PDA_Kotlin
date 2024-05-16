package cz.cvut.fel.sit.pda.screens.accounts

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.components.BasicAppBar
import cz.cvut.fel.sit.pda.components.GeldsBottomBar
import cz.cvut.fel.sit.pda.database.BankEntity
import cz.cvut.fel.sit.pda.models.BankCard
import cz.cvut.fel.sit.pda.ui.theme.DefaultColor
import cz.cvut.fel.sit.pda.ui.theme.Green700
import cz.cvut.fel.sit.pda.ui.theme.Indigo50

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CardDetailScreen(
    navController: NavHostController,
    bank: BankEntity,
    onDelete: (BankEntity) -> Unit
) {
    Scaffold(
        topBar = {
            BasicAppBar(
                title = "Card Details",
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
                // Display card information
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Card Name: ${bank.name}",
                            style = typography.titleMedium
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { navController.navigate("editCard/${bank.name}") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green700
                    )
                ) {
                    Text("Edit",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Indigo50
                    )
                }
                Button(
                    onClick = {
                        onDelete(bank)
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Delete",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Indigo50
                    )
                }
            }
        }
    }
}
