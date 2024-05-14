package cz.cvut.fel.sit.pda.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers

class BankCardRepository(private val bankCardDao: BankCardDao) {

    fun getAllCards(): Flow<List<BankCardEntity>> {
        return bankCardDao.getAllCards().flowOn(Dispatchers.IO)
    }

    suspend fun insert(card: BankCardEntity) {
        bankCardDao.insert(card)
    }

    suspend fun getCardByName(name: String): BankCardEntity? {
        return bankCardDao.getCardByName(name)
    }

    suspend fun deleteCardByName(name: String) {
        bankCardDao.deleteCardByName(name)
    }
}
