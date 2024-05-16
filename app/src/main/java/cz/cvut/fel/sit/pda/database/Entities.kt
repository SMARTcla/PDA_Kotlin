package cz.cvut.fel.sit.pda.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.cvut.fel.sit.pda.models.Transaction
import java.time.LocalDate

@Entity(
    tableName = "banks"
)
data class BankEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)


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

    private fun convertDateLongToLocalDate(): LocalDate {
        val day = (date / 1000000).toInt()
        val month = (date / 10000).toInt() % 100
        val year = (date % 10000).toInt()

        return LocalDate.of(year, month, day)
    }

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
