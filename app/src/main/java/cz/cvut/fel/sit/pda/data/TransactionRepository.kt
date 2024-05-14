package cz.cvut.fel.sit.pda.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers

class TransactionRepository(private val transactionDao: TransactionDao) {

    fun getAllTransactions(): Flow<List<TransactionEntity>> {
        return transactionDao.getAllTransactions().flowOn(Dispatchers.IO)
    }

    suspend fun insert(transaction: TransactionEntity) {
        transactionDao.insert(transaction)
    }

    suspend fun update(transaction: TransactionEntity) {
        transactionDao.update(transaction)
    }

    suspend fun getTransactionById(id: String): TransactionEntity? {
        return transactionDao.getTransactionById(id)
    }

    suspend fun deleteTransactionById(id: String) {
        transactionDao.deleteTransactionById(id)
    }
}
