package cz.cvut.fel.sit.pda.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

/**
 * Composable function for displaying the settings content.
 *
 * @param navController NavHostController for navigation.
 * @param paddingValues Padding values for the content.
 * @param onNotificationsClick Callback function to be invoked when "Notifications" is clicked.
 * @param onAddReceiptsClick Callback function to be invoked when "Add receipts" is clicked.
 * @param onMyReceiptsClick Callback function to be invoked when "My receipts" is clicked.
 * @param onVersionClick Callback function to be invoked when "Version" is clicked.
 * @param onAboutClick Callback function to be invoked when "About us" is clicked.
 * @param onSupportClick Callback function to be invoked when "Support" is clicked.
 */
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