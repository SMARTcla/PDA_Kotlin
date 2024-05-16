package cz.cvut.fel.sit.pda.screens.transactions.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cz.cvut.fel.sit.pda.database.TransactionEntity
import cz.cvut.fel.sit.pda.database.TransactionType
import cz.cvut.fel.sit.pda.screens.transactions.TransactionScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class TransactionViewModel(
    transactionScreenUiState: TransactionScreenUiState = TransactionScreenUiState()
) : ViewModel() {

    private val _uiState = MutableStateFlow(transactionScreenUiState)
    val uiState = _uiState.asStateFlow()

    private fun convertLocalDateToLong(localDate: LocalDate): Long {
        return (
                localDate.dayOfMonth * 1000000 + (localDate.monthValue) * 10000 + localDate.year
                ).toLong()
    }

    fun updateName(value: String) {
        _uiState.update {
            it.copy(name = value)
        }
    }

    fun updateAmount(value: String) {
        _uiState.update {
            it.copy(amount = value)
        }
    }

    fun updateType(value: TransactionType) {
        _uiState.update {
            it.copy(type = value)
        }
    }

    fun updateDate(value: LocalDate) {
        _uiState.update {
            it.copy(date = value)
        }
    }

    fun updateCategory(value: String) {
        _uiState.update {
            it.copy(category = value)
        }
    }

    fun updateCardName(value: String) {
        _uiState.update {
            it.copy(cardName = value)
        }
    }

    fun getTransaction(): TransactionEntity {
        return TransactionEntity(
            id = uiState.value.transactionId,
            name = uiState.value.name,
            amount = uiState.value.amount.toLong(),
            type = uiState.value.type,
            date = convertLocalDateToLong(uiState.value.date),
            card = uiState.value.cardName
        )
    }

}

class TransactionViewModelFactory(
    private val transactionScreenUiState: TransactionScreenUiState
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TransactionViewModel(transactionScreenUiState) as T
    }
}