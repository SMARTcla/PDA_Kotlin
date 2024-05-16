package cz.cvut.fel.sit.pda.database

sealed interface GeldEvent {
    object SaveTransaction : GeldEvent
    object UpdateTransaction : GeldEvent
    data class DeleteTransactionById(val transactionId: Long) : GeldEvent
    object SaveBank : GeldEvent
    object UpdateBank : GeldEvent
    data class DeleteBankById(val bankId: Long) : GeldEvent

}