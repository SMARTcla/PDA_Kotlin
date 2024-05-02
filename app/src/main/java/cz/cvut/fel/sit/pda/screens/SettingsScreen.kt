package cz.cvut.fel.sit.pda.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.compose.material3.*
import cz.cvut.fel.sit.pda.ui.theme.PDATheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController?) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SettingsDrawerContent(
                onDestinationClicked = {
                    scope.launch {
                        drawerState.close()
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Settings") },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController?.popBackStack()
                        }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            SettingsContent(paddingValues)
        }
    }
}

@Composable
fun SettingsDrawerContent(onDestinationClicked: () -> Unit) {
    Column(modifier = Modifier.padding(start = 24.dp, top = 48.dp)) {
        Text("Notifications", modifier = Modifier.clickable(onClick = onDestinationClicked))
        Text("Version", modifier = Modifier.clickable(onClick = onDestinationClicked))
        Text("Rate us", modifier = Modifier.clickable(onClick = onDestinationClicked))
        Text("Support", modifier = Modifier.clickable(onClick = onDestinationClicked))
    }
}

@Composable
fun SettingsContent(paddingValues: PaddingValues) {
    Column(modifier = Modifier.padding(paddingValues)) {
        OptionItem("Notifications")
        OptionItem("Version")
        OptionItem("Rate us")
        OptionItem("Support")
    }
}

@Composable
fun OptionItem(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                //TODO
                /* Add actions here */
            }
            .padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    PDATheme {
        SettingsScreen(null)
    }
}