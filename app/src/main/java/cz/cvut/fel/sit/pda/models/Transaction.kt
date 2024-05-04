package cz.cvut.fel.sit.pda.models

import java.time.LocalDate
import java.util.UUID

data class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val amount: Double,
    val type: TransactionType,
    val date: LocalDate,
    val category: String,
    val cardName: String
)