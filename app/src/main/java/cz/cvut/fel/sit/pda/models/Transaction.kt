package cz.cvut.fel.sit.pda.models

import java.time.LocalDate

data class Transaction(
    val name: String,
    val amount: Double,
    val type: TransactionType,
    val date: LocalDate,
    val category: String,
    val cardName: String
)