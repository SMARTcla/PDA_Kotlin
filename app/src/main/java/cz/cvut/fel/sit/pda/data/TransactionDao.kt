package cz.cvut.fel.sit.pda.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: TransactionEntity)

    @Update
    suspend fun update(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: String): TransactionEntity?

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransactionById(id: String)

    @Query("SELECT * FROM transactions")
    fun getAllTransactions(): Flow<List<TransactionEntity>>
}
