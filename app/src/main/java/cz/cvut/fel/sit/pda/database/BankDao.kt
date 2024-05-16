package cz.cvut.fel.sit.pda.database;

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BankDao {
    @Insert
    suspend fun insertBank(bank:BankEntity): Long

    @Update
    suspend fun updateBank(bank: BankEntity)

    @Query("DELETE FROM banks WHERE id = :id")
    suspend fun deleteBankById(id: Long): Int

    @Query("SELECT * FROM banks WHERE id = :id")
    fun getBankById(id: Long): Flow<BankEntity?>

    @Query("SELECT * FROM banks ORDER BY name ASC")
    fun getAllBanks(): Flow<List<BankEntity>>
}