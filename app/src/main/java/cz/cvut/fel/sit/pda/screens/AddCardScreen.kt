package cz.cvut.fel.sit.pda.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.models.BankCard
import cz.cvut.fel.sit.pda.utils.TemporaryDatabase

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddCardScreen(navController: NavHostController) {
    var cardName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add New Card") }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            })
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = cardName,
                onValueChange = { cardName = it },
                label = { Text("Card Name") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (cardName.isNotEmpty()) {
                        TemporaryDatabase.bankCards.add(BankCard(cardName))
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Card")
            }
        }
    }
}
