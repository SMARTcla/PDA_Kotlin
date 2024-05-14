package cz.cvut.fel.sit.pda.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fel.sit.pda.data.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GeldViewModel(application: Application, database: AppDatabase) : AndroidViewModel(application) {

    private val transactionRepository: TransactionRepository
    private val bankCardRepository: BankCardRepository

    init {
        transactionRepository = TransactionRepository(database.transactionDao())
        bankCardRepository = BankCardRepository(database.bankCardDao())
    }

    val transactions: Flow<List<TransactionEntity>> = transactionRepository.getAllTransactions()
    val bankCards: Flow<List<BankCardEntity>> = bankCardRepository.getAllCards()

    fun insertTransaction(transaction: TransactionEntity) = viewModelScope.launch {
        transactionRepository.insert(transaction)
    }

    fun updateTransaction(transaction: TransactionEntity) = viewModelScope.launch {
        transactionRepository.update(transaction)
    }

    fun deleteTransactionById(id: String) = viewModelScope.launch {
        transactionRepository.deleteTransactionById(id)
    }

    fun insertBankCard(card: BankCardEntity) = viewModelScope.launch {
        bankCardRepository.insert(card)
    }

    fun deleteBankCardByName(name: String) = viewModelScope.launch {
        bankCardRepository.deleteCardByName(name)
    }
}
