package cz.cvut.fel.sit.pda.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String,
    val name: String,
    val amount: Double,
    val type: String,
    val date: LocalDate,
    val category: String,
    val cardName: String
)
