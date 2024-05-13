package cz.cvut.fel.sit.pda.screens.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.navigation.compose.rememberNavController
import cz.cvut.fel.sit.pda.GeldScreen
import cz.cvut.fel.sit.pda.ui.theme.PDATheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    var showVersionDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showSupportDialog by remember { mutableStateOf(false) }
    var showNotificationsDialog by remember { mutableStateOf(false) }
    var enableNotifications by remember { mutableStateOf(false) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SettingsDrawerContent(navController = navController, onDestinationClicked = {
                scope.launch {
                    drawerState.close()
                }
            })
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Settings") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            SettingsContent(navController, paddingValues,
                onNotificationsClick = { showNotificationsDialog = true },
                onVersionClick = { showVersionDialog = true },
                onAboutClick = { showAboutDialog = true },
                onSupportClick = { showSupportDialog = true },
                )
            if (showVersionDialog) {
                VersionScreen(onDismiss = { showVersionDialog = false })
            }
            if (showAboutDialog) {
                AboutDialog(onDismiss = { showAboutDialog = false })
            }
            if (showSupportDialog) {
                SupportDialog(onDismiss = { showSupportDialog = false })
            }
            if (showNotificationsDialog) {
                NotificationsDialog(
                    enableNotifications = enableNotifications,
                    onEnableChange = { enabled ->
                        enableNotifications = enabled
                        // Сохраните настройку уведомлений здесь
                    },
                    onDismiss = { showNotificationsDialog = false }
                )
            }
        }
    }
}

@Composable
fun SettingsDrawerContent(navController: NavHostController, onDestinationClicked: () -> Unit) {
    Column(modifier = Modifier.padding(start = 24.dp, top = 48.dp)) {
        Text("Notifications", modifier = Modifier.clickable(onClick = onDestinationClicked))
        Text("Version", modifier = Modifier.clickable(onClick = onDestinationClicked))
        Text("About us", modifier = Modifier.clickable(onClick = onDestinationClicked))
        Text("Support", modifier = Modifier.clickable(onClick = onDestinationClicked))
    }
}

@Composable
fun SettingsContent(navController: NavHostController,
                    paddingValues: PaddingValues,
                    onNotificationsClick: () -> Unit,
                    onVersionClick: () -> Unit,
                    onAboutClick: () -> Unit,
                    onSupportClick: () -> Unit) {
    Column(modifier = Modifier.padding(paddingValues)) {
        OptionItem("Notifications", onClick = onNotificationsClick)
        OptionItem("Version", onClick = onVersionClick)
        OptionItem("About us", onClick = onAboutClick)
        OptionItem("Support", onClick = onSupportClick)
    }
}

@Composable
fun OptionItem(text: String, onClick: (() -> Unit)? = null) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick ?: {})
            .padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    PDATheme {
        // You need to provide a real navController here for previews involving navigation
        SettingsScreen(navController = rememberNavController())
    }
}