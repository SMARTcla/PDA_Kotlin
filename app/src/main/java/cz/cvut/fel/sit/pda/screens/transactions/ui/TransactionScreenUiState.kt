package cz.cvut.fel.sit.pda.screens.transactions.ui

import cz.cvut.fel.sit.pda.database.TransactionType
import java.time.LocalDate

/**
 * Data class representing the UI state of a transaction screen.
 *
 * @property name The name or description of the transaction.
 * @property amount The amount of the transaction, represented as a string.
 * @property type The type of the transaction, represented by [TransactionType].
 * @property date The date of the transaction.
 * @property category The category of the transaction.
 * @property cardName The name of the card used for the transaction.
 * @property transactionId The unique identifier of the transaction.
 */
data class TransactionScreenUiState(
    val name: String = "",
    val amount: String = "0",
    val type: TransactionType = TransactionType.RESTAURANT,
    val date: LocalDate = LocalDate.now(),
    val category: String = "",
    val cardName: String = "",
    val transactionId: Long = 0
)