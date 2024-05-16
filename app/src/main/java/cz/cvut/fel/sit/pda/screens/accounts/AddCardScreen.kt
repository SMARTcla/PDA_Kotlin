package cz.cvut.fel.sit.pda.screens.accounts

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.components.BasicAppBar
import cz.cvut.fel.sit.pda.components.GeldsBottomBar
import cz.cvut.fel.sit.pda.database.BankEntity
import cz.cvut.fel.sit.pda.ui.theme.DeepPurple500
import cz.cvut.fel.sit.pda.ui.theme.DefaultColor
import cz.cvut.fel.sit.pda.ui.theme.Grey50

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddCardScreen(
    navController: NavHostController,
    cards: List<BankEntity>,
    saveBank: (BankEntity) -> Unit
) {
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
            Column(
                modifier = Modifier
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
                            saveBank(BankEntity(name = cardName))
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