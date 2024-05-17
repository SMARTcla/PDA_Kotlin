package cz.cvut.fel.sit.pda.screens.transactions.ui

import cz.cvut.fel.sit.pda.database.TransactionType
import java.time.LocalDate

data class TransactionScreenUiState(
    val name: String = "",
    val amount: String = "0",
    val type: TransactionType = TransactionType.RESTAURANT,
    val date: LocalDate = LocalDate.now(),
    val category: String = "",
    val cardName: String = "",
    val transactionId: Long = 0
)