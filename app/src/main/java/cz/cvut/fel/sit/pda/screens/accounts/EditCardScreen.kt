package cz.cvut.fel.sit.pda.screens.accounts


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.components.BasicAppBar
import cz.cvut.fel.sit.pda.components.GeldsBottomBar
import cz.cvut.fel.sit.pda.database.BankEntity
import cz.cvut.fel.sit.pda.ui.theme.DefaultColor
import cz.cvut.fel.sit.pda.ui.theme.Green700
import cz.cvut.fel.sit.pda.ui.theme.Grey50
import cz.cvut.fel.sit.pda.ui.theme.Indigo50


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditCardScreen(
    navController: NavHostController,
    card: BankEntity,
    onUpdate: (BankEntity) -> Unit
) {
    var cardName by remember { mutableStateOf(card.name) }

    Scaffold(
        topBar = {
            BasicAppBar(
                title = "Edit Card",
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
                    .padding(horizontal = 16.dp, vertical = 20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = cardName,
                    onValueChange = { cardName = it },
                    label = { Text("Card Name") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Indigo50,
                        cursorColor = Indigo50,
                        focusedBorderColor = Indigo50,
                        unfocusedBorderColor = Indigo50,
                        focusedLabelColor = Indigo50,
                        unfocusedLabelColor = Indigo50
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = {
                        onUpdate(card.copy(name = cardName))
                        navController.popBackStack()
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Green700
                    )
                ) {
                    Text("Save Changes", color = Grey50)
                }
            }
        }
    }
}


