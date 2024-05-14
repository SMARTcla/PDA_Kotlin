package cz.cvut.fel.sit.pda.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bank_cards")
data class BankCardEntity(
    @PrimaryKey val name: String
)
