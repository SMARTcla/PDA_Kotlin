package cz.cvut.fel.sit.pda.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface for the TransactionEntity.
 * Defines methods for performing CRUD operations on the transactions table.
 */
@Dao
interface TransactionDao {
    /**
     * Insert a new transaction into the transactions table.
     *
     * @param transaction The transaction to insert.
     * @return The ID of the inserted transaction.
     */
    @Insert
    suspend fun insertTransaction(transaction: TransactionEntity): Long

    /**
     * Update an existing transaction in the transactions table.
     *
     * @param transaction The transaction to update.
     */
    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

    /**
     * Delete a transaction from the transactions table by its ID.
     *
     * @param id The ID of the transaction to delete.
     * @return The number of transactions deleted (should be 0 or 1).
     */
    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransactionById(id: Long): Int

    /**
     * Get a transaction from the transactions table by its ID.
     *
     * @param id The ID of the transaction to retrieve.
     * @return A Flow emitting the retrieved transaction, or null if not found.
     */
    @Query("SELECT * FROM transactions WHERE id = :id")
    fun getTransactionById(id: Long): Flow<TransactionEntity?>


    /**
     * Get all transactions from the transactions table, ordered by name in ascending order.
     *
     * @return A Flow emitting a list of all transactions.
     */
    @Query("SELECT * FROM transactions ORDER BY name ASC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>
}