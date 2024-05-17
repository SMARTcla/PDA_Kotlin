package cz.cvut.fel.sit.pda.screens.transactions.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TransactionViewModelFactory(
    private val transactionScreenUiState: TransactionScreenUiState
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TransactionViewModel(transactionScreenUiState) as T
    }
}