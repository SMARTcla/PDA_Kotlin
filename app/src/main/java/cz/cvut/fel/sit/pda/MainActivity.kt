package cz.cvut.fel.sit.pda

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cz.cvut.fel.sit.pda.database.GeldDatabase
import cz.cvut.fel.sit.pda.database.GeldViewModel
import cz.cvut.fel.sit.pda.services.NotificationService.Companion.CHANNEL_ID
import cz.cvut.fel.sit.pda.ui.theme.PDATheme

/**
 * Main activity of the application.
 */
class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController

    // Lazy initialization of the database instance
    val db by lazy { GeldDatabase.getInstance(this) }

    // View model initialization
    private val viewModel: GeldViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return GeldViewModel(db.transactionDao, db.bankDao) as T
            }
        }
    }

    /**
     * Called when the activity is starting. Sets up the content view using Jetpack Compose
     * and initializes the UI.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut
     * down, this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PDATheme {
                viewModel.fetchDataOnStart()
                navHostController = rememberNavController()
                AppNavigation(
                    navController = navHostController,
                    geldViewModel = viewModel
                )
            }

            createNotificationChannel()
        }
    }

    /**
     * Creates a notification channel for the application.
     */
    private fun createNotificationChannel() {
        val name = "Reminder Channel"
        val descriptionText = "Channel for notifications"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}