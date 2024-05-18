package cz.cvut.fel.sit.pda.screens.transactions.ui

import androidx.lifecycle.ViewModel
import cz.cvut.fel.sit.pda.database.TransactionEntity
import cz.cvut.fel.sit.pda.database.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class TransactionViewModel(
    transactionScreenUiState: TransactionScreenUiState = TransactionScreenUiState()
) : ViewModel() {

    companion object {
        private const val DAY_MULTIPLIER = 1000000
        private const val MONTH_MULTIPLIER = 10000
    }

    private val _uiState = MutableStateFlow(transactionScreenUiState)
    val uiState = _uiState.asStateFlow()

    /**
     * Converts a [LocalDate] to a Long representation.
     *
     * The conversion formula is:
     * (dayOfMonth * [DAY_MULTIPLIER]) + (monthValue * [MONTH_MULTIPLIER]) + year
     *
     * @param localDate The [LocalDate] to convert.
     * @return The long representation of the date.
     */
    private fun convertLocalDateToLong(localDate: LocalDate): Long {
        return (
                localDate.dayOfMonth * DAY_MULTIPLIER
                        + (localDate.monthValue) * MONTH_MULTIPLIER
                        + localDate.year
                ).toLong()
    }

    /**
     * Updates the name in the UI state.
     *
     * @param value The new name to set.
     */
    fun updateName(value: String) {
        _uiState.update {
            it.copy(name = value)
        }
    }

    /**
     * Updates the amount in the UI state.
     *
     * @param value The new amount to set.
     */
    fun updateAmount(value: String) {
        _uiState.update {
            it.copy(amount = value)
        }
    }

    /**
     * Updates the transaction type in the UI state.
     *
     * @param value The new transaction type to set.
     */
    fun updateType(value: TransactionType) {
        _uiState.update {
            it.copy(type = value)
        }
    }

    /**
     * Updates the date in the UI state.
     *
     * @param value The new date to set.
     */
    fun updateDate(value: LocalDate) {
        _uiState.update {
            it.copy(date = value)
        }
    }

    /**
     * Updates the category in the UI state.
     *
     * @param value The new category to set.
     */
    fun updateCategory(value: String) {
        _uiState.update {
            it.copy(category = value)
        }
    }

    /**
     * Updates the card name in the UI state.
     *
     * @param value The new card name to set.
     */
    fun updateCardName(value: String) {
        _uiState.update {
            it.copy(cardName = value)
        }
    }

    /**
     * Creates a [TransactionEntity] from the current UI state.
     *
     * @return A new [TransactionEntity] with the current state values.
     */
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

