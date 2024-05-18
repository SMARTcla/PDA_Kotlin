package cz.cvut.fel.sit.pda.screens.transactions.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Factory class for creating an instance of [TransactionViewModel].
 *
 * This factory is used to pass the [TransactionScreenUiState] to the ViewModel
 * when it is created.
 *
 * @param transactionScreenUiState The state of the UI that needs to be managed by the ViewModel.
 */
class TransactionViewModelFactory(
    private val transactionScreenUiState: TransactionScreenUiState
) : ViewModelProvider.NewInstanceFactory() {

    /**
     * Creates a new instance of the given [ViewModel] class.
     *
     * @param modelClass The class of the ViewModel to create an instance of.
     * @return A newly created [TransactionViewModel] instance with the provided UI state.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TransactionViewModel(transactionScreenUiState) as T
    }
}