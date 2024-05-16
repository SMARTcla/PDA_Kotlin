package cz.cvut.fel.sit.pda.models

import cz.cvut.fel.sit.pda.database.TransactionEntity
import cz.cvut.fel.sit.pda.database.TransactionType
import java.time.LocalDate

data class Transaction(
    val id: String,
    val name: String,
    val amount: Long,
    val type: TransactionType,
    val date: LocalDate,
    val cardName: String
) {

    private fun convertDateLocalToDateLong(): Long {
        return (date.dayOfMonth * 1000000 + (date.monthValue + 1) * 10000 + date.year).toLong()
    }

    fun toEntity(): TransactionEntity {
        return TransactionEntity(
            id = id.toLong(),
            name = name,
            amount = amount,
            type = type,
            date = convertDateLocalToDateLong(),
            card = cardName,
        )
    }
}