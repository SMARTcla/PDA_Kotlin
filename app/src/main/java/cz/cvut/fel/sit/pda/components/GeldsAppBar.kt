package cz.cvut.fel.sit.pda.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.GeldScreen
import cz.cvut.fel.sit.pda.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicAppBar(
    title: String,
    navController: NavHostController,
    canNavigateBack: Boolean,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = Modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = {
                navController.navigate(GeldScreen.Settings.name)
            }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = stringResource(id = R.string.settings_button)
                )
            }
        }
    )
}
