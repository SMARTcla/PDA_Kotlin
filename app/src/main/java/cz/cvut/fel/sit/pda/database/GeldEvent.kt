package cz.cvut.fel.sit.pda.database

/**
 * Sealed interface representing events related to transactions and banks.
 */
sealed interface GeldEvent {
    data object SaveTransaction : GeldEvent
    data object UpdateTransaction : GeldEvent
    data class DeleteTransactionById(val transactionId: Long) : GeldEvent
    data object SaveBank : GeldEvent
    data object UpdateBank : GeldEvent
    data class DeleteBankById(val bankId: Long) : GeldEvent

}