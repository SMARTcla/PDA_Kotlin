package cz.cvut.fel.sit.pda.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.components.BasicAppBar
import cz.cvut.fel.sit.pda.components.GeldsBottomBar
import cz.cvut.fel.sit.pda.models.BankCard
import cz.cvut.fel.sit.pda.ui.theme.DeepPurple500
import cz.cvut.fel.sit.pda.ui.theme.DefaultColor
import cz.cvut.fel.sit.pda.ui.theme.Grey400
import cz.cvut.fel.sit.pda.ui.theme.Grey50
import cz.cvut.fel.sit.pda.ui.theme.Grey900
import cz.cvut.fel.sit.pda.ui.theme.PurpleGrey80
import cz.cvut.fel.sit.pda.ui.theme.white
import cz.cvut.fel.sit.pda.utils.TemporaryDatabase

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddCardScreen(navController: NavHostController) {
    var cardName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            BasicAppBar(
                title = "Add New Card",
                navController = navController,
                canNavigateBack = true,
                onNavigateBack = { navController.popBackStack() }
            )
        },
        bottomBar = {
            GeldsBottomBar(navController)
        }
    ) { innerPadding ->
        Surface(color = DefaultColor, modifier = Modifier.padding(innerPadding)) {
            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
            ) {
                OutlinedTextField(
                    value = cardName,
                    onValueChange = { cardName = it },
                    label = { Text("Card Name") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        cursorColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (cardName.isNotEmpty()) {
                            TemporaryDatabase.bankCards.add(BankCard(cardName))
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = DeepPurple500)
                ) {
                    Text("Add Card", color = Grey50)
                }
            }
        }
    }
}