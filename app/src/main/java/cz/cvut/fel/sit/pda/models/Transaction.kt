package cz.cvut.fel.sit.pda.models

import cz.cvut.fel.sit.pda.database.TransactionEntity
import cz.cvut.fel.sit.pda.database.TransactionType
import java.time.LocalDate

/**
 * A data class representing a transaction.
 *
 * @property id The unique identifier of the transaction.
 * @property name The name of the transaction.
 * @property amount The amount of the transaction.
 * @property type The type of the transaction.
 * @property date The date of the transaction.
 * @property cardName The name of the card associated with the transaction.
 */
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