package cz.cvut.fel.sit.pda.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BankCardDao {
    @Insert
    suspend fun insert(card: BankCardEntity)

    @Query("SELECT * FROM bank_cards WHERE name = :name")
    suspend fun getCardByName(name: String): BankCardEntity?

    @Query("DELETE FROM bank_cards WHERE name = :name")
    suspend fun deleteCardByName(name: String)

    @Query("SELECT * FROM bank_cards")
    fun getAllCards(): Flow<List<BankCardEntity>>
}
