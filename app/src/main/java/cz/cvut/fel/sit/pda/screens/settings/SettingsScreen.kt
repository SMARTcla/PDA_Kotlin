package cz.cvut.fel.sit.pda.screens.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cz.cvut.fel.sit.pda.screens.settings.receipts.AddReceiptsDialog
import cz.cvut.fel.sit.pda.screens.settings.receipts.MyReceiptsDialog
import cz.cvut.fel.sit.pda.services.NotificationService
import cz.cvut.fel.sit.pda.ui.theme.PDATheme
import kotlinx.coroutines.launch

private const val PREFS_NAME = "settings_prefs"
private const val PREF_ENABLE_NOTIFICATIONS = "enable_notifications"

/**
 * Composable function for displaying the Settings screen.
 *
 * @param navController NavHostController for navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    var showVersionDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showSupportDialog by remember { mutableStateOf(false) }
    var showNotificationsDialog by remember { mutableStateOf(false) }
    var showAddReceiptsDialog by remember { mutableStateOf(false) }
    var showMyReceiptsDialog by remember { mutableStateOf(false) }
    var enableNotifications by remember {
        mutableStateOf(sharedPreferences.getBoolean(PREF_ENABLE_NOTIFICATIONS, false))
    }
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
                        with(sharedPreferences.edit()) {
                            putBoolean(PREF_ENABLE_NOTIFICATIONS, enabled)
                            apply()
                        }
                        if (enabled) {
                            context.startService(Intent(context, NotificationService::class.java))
                        } else {
                            context.stopService(Intent(context, NotificationService::class.java))
                        }
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
                MyReceiptsDialog(
                    imageUri = imageUri!!,
                    onDismiss = { showMyReceiptsDialog = false })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    PDATheme {
        SettingsScreen(navController = rememberNavController())
    }
}
