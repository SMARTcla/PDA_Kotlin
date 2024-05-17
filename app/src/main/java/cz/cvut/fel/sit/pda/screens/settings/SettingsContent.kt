package cz.cvut.fel.sit.pda.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

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