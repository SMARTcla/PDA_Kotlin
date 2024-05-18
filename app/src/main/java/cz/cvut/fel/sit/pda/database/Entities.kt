package cz.cvut.fel.sit.pda.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.cvut.fel.sit.pda.models.Transaction
import java.time.LocalDate

/**
 * Entity class representing a bank.
 *
 * @param id   The ID of the bank entity (auto-generated).
 * @param name The name of the bank.
 */
@Entity(
    tableName = "banks"
)
data class BankEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)

/**
 * Entity class representing a transaction.
 *
 * @param id     The ID of the transaction entity (auto-generated).
 * @param name   The name of the transaction.
 * @param amount The amount of the transaction.
 * @param type   The type of the transaction (e.g., SALARY, BENEFITS).
 * @param date   The date of the transaction.
 * @param card   The name of the card associated with the transaction.
 */
@Entity(
    tableName = "transactions"
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    val amount: Long,
    val type: TransactionType,
    val date: Long,
    val card: String
) {

    /**
     * Converts the long date value to LocalDate format.
     *
     * @return The LocalDate representation of the date.
     */
    private fun convertDateLongToLocalDate(): LocalDate {
        val day = (date / 1000000).toInt()
        val month = (date / 10000).toInt() % 100
        val year = (date % 10000).toInt()

        return LocalDate.of(year, month, day)
    }

    /**
     * Converts the TransactionEntity object to a Transaction object.
     *
     * @return The Transaction object representation.
     */
    fun toTransaction(): Transaction {
        return Transaction(
            id = id.toString(),
            name = name,
            amount = amount,
            type = type,
            date = convertDateLongToLocalDate(),
            cardName = card
        )
    }
}
