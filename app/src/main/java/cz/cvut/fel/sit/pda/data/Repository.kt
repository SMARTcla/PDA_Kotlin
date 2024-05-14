//package cz.cvut.fel.sit.pda.data
//
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//
//class TransactionRepository(private val transactionDao: TransactionDao) {
//
//    suspend fun insert(transaction: TransactionEntity) {
//        withContext(Dispatchers.IO) {
//            transactionDao.insert(transaction)
//        }
//    }
//
//    suspend fun update(transaction: TransactionEntity) {
//        withContext(Dispatchers.IO) {
//            transactionDao.update(transaction)
//        }
//    }
//
//    suspend fun getTransactionById(id: String): TransactionEntity? {
//        return withContext(Dispatchers.IO) {
//            transactionDao.getTransactionById(id)
//        }
//    }
//
//    suspend fun deleteTransactionById(id: String) {
//        withContext(Dispatchers.IO) {
//            transactionDao.deleteTransactionById(id)
//        }
//    }
//
//    suspend fun getAllTransactions(): List<TransactionEntity> {
//        return withContext(Dispatchers.IO) {
//            transactionDao.getAllTransactions()
//        }
//    }
//}
//
//class BankCardRepository(private val bankCardDao: BankCardDao) {
//
//    suspend fun insert(card: BankCardEntity) {
//        withContext(Dispatchers.IO) {
//            bankCardDao.insert(card)
//        }
//    }
//
//    suspend fun getCardByName(name: String): BankCardEntity? {
//        return withContext(Dispatchers.IO) {
//            bankCardDao.getCardByName(name)
//        }
//    }
//
//    suspend fun deleteCardByName(name: String) {
//        withContext(Dispatchers.IO) {
//            bankCardDao.deleteCardByName(name)
//        }
//    }
//
//    suspend fun getAllCards(): List<BankCardEntity> {
//        return withContext(Dispatchers.IO) {
//            bankCardDao.getAllCards()
//        }
//    }
//}
