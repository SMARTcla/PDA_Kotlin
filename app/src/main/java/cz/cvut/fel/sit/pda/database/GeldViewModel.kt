package cz.cvut.fel.sit.pda.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fel.sit.pda.models.BankCard
import cz.cvut.fel.sit.pda.models.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GeldViewModel(
    private val transactionDao: TransactionDao,
    private val bankDao: BankDao,
) : ViewModel() {

    private val _transactions = MutableStateFlow((listOf<Transaction>()))
    private val _banks = MutableStateFlow(listOf<BankEntity>())

    val uiState = combine(
        _transactions,
        _banks
    ) { transactions, banks ->
        TransactionState(
            transactions = transactions,
            banks = banks,
            cards = banks.map { BankCard(it.name) }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TransactionState())


    suspend fun saveTransaction(transaction: TransactionEntity) {
        transactionDao.insertTransaction(transaction)
        fetchTransactions()
    }

    suspend fun updateTransaction(transaction: TransactionEntity) {
        transactionDao.updateTransaction(transaction)
        fetchTransactions()
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransactionById(transaction.id.toLong())
        fetchTransactions()
    }


    suspend fun saveBank(bank: BankEntity) {
        bankDao.insertBank(bank)
        fetchBanks()
    }

    suspend fun updateBank(bank: BankEntity) {
        bankDao.updateBank(bank)
        fetchBanks()
    }

    suspend fun deleteBank(bank: BankEntity) {
        bankDao.deleteBankById(bank.id)
        fetchBanks()
    }


    private fun fetchTransactions() {
        viewModelScope.launch {
            transactionDao.getAllTransactions().collect { transactions ->
                _transactions.update { transactions.map { it.toTransaction() } }
            }
        }
    }

    private fun fetchBanks() {
        viewModelScope.launch {
            bankDao.getAllBanks().collect { banks ->
                _banks.update { banks }
            }
        }
    }

    fun fetchDataOnStart() {
        fetchTransactions()
        fetchBanks()
    }

}


