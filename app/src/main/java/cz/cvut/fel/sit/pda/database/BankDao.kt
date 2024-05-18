package cz.cvut.fel.sit.pda.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for interacting with BankEntity in the database.
 */
@Dao
interface BankDao {

    /**
     * Inserts a bank into the database.
     *
     * @param bank The bank to insert.
     * @return The ID of the inserted bank.
     */
    @Insert
    suspend fun insertBank(bank: BankEntity): Long

    /**
     * Updates a bank in the database.
     *
     * @param bank The bank to update.
     */
    @Update
    suspend fun updateBank(bank: BankEntity)

    /**
     * Deletes a bank from the database by its ID.
     *
     * @param id The ID of the bank to delete.
     * @return The number of banks deleted (should be 0 or 1).
     */
    @Query("DELETE FROM banks WHERE id = :id")
    suspend fun deleteBankById(id: Long): Int

    /**
     * Retrieves a bank from the database by its ID.
     *
     * @param id The ID of the bank to retrieve.
     * @return A Flow emitting the bank entity, or null if not found.
     */
    @Query("SELECT * FROM banks WHERE id = :id")
    fun getBankById(id: Long): Flow<BankEntity?>

    /**
     * Retrieves all banks from the database, ordered by name.
     *
     * @return A Flow emitting the list of bank entities.
     */
    @Query("SELECT * FROM banks ORDER BY name ASC")
    fun getAllBanks(): Flow<List<BankEntity>>
}