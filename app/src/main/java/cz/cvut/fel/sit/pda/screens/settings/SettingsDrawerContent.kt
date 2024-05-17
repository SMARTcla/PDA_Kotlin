package cz.cvut.fel.sit.pda.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SettingsDrawerContent(navController: NavHostController, onDestinationClicked: () -> Unit) {
    Column(modifier = Modifier.padding(start = 24.dp, top = 48.dp)) {
        Text("Notifications", modifier = Modifier.clickable(onClick = onDestinationClicked))
        Text("Add receipts", modifier = Modifier.clickable(onClick = onDestinationClicked))
        Text("My receipts", modifier = Modifier.clickable(onClick = onDestinationClicked))
        Text("Version", modifier = Modifier.clickable(onClick = onDestinationClicked))
        Text("About us", modifier = Modifier.clickable(onClick = onDestinationClicked))
        Text("Support", modifier = Modifier.clickable(onClick = onDestinationClicked))
    }
}