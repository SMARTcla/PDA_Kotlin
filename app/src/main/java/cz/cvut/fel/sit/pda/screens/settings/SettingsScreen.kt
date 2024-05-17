package cz.cvut.fel.sit.pda.screens.settings

import MyReceiptsDialog
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cz.cvut.fel.sit.pda.screens.settings.receipts.AddReceiptsDialog
import cz.cvut.fel.sit.pda.ui.theme.PDATheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    var showVersionDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showSupportDialog by remember { mutableStateOf(false) }
    var showNotificationsDialog by remember { mutableStateOf(false) }
    var showAddReceiptsDialog by remember { mutableStateOf(false) }
    var showMyReceiptsDialog by remember { mutableStateOf(false) }
    var enableNotifications by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

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
            SettingsContent(
                navController, paddingValues,
                onNotificationsClick = { showNotificationsDialog = true },
                onAddReceiptsClick = { showAddReceiptsDialog = true },
                onMyReceiptsClick = { showMyReceiptsDialog = true },
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

            if (showAddReceiptsDialog) {
                AddReceiptsDialog(
                    onDismiss = { showAddReceiptsDialog = false },
                    onPictureSelected = { uri -> imageUri = uri }
                )
            }
            if (showMyReceiptsDialog && imageUri != null) {
                MyReceiptsDialog(imageUri = imageUri!!, onDismiss = { showMyReceiptsDialog = false })
            }
        }
    }
}

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

@Composable
fun SettingsContent(
    navController: NavHostController,
    paddingValues: PaddingValues,
    onNotificationsClick: () -> Unit,
    onAddReceiptsClick: () -> Unit,
    onMyReceiptsClick: () -> Unit,
    onVersionClick: () -> Unit,
    onAboutClick: () -> Unit,
    onSupportClick: () -> Unit
) {
    Column(modifier = Modifier.padding(paddingValues)) {
        OptionItem("Notifications", onClick = onNotificationsClick)
        OptionItem("Add receipts", onClick = onAddReceiptsClick)
        OptionItem("My receipts", onClick = onMyReceiptsClick)
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