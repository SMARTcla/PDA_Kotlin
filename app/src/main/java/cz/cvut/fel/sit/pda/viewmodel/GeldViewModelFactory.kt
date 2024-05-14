package cz.cvut.fel.sit.pda.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cz.cvut.fel.sit.pda.data.AppDatabase

class GeldViewModelFactory(
    private val application: Application,
    private val database: AppDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GeldViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GeldViewModel(application, database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
