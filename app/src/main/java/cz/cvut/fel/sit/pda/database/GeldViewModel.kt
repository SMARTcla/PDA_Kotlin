package cz.cvut.fel.sit.pda.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fel.sit.pda.models.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing transactions and banks data.
 *
 * @param transactionDao The DAO for transaction-related database operations.
 * @param bankDao The DAO for bank-related database operations.
 */
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
            banks = banks
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TransactionState())

    /**
     * Saves a transaction into the database and updates the transaction list.
     *
     * @param transaction The transaction to be saved.
     */
    suspend fun saveTransaction(transaction: TransactionEntity) {
        transactionDao.insertTransaction(transaction)
        fetchTransactions()
    }

    /**
     * Updates a transaction in the database and refreshes the transaction list.
     *
     * @param transaction The transaction to be updated.
     */
    suspend fun updateTransaction(transaction: TransactionEntity) {
        transactionDao.updateTransaction(transaction)
        fetchTransactions()
    }

    /**
     * Deletes a transaction from the database and updates the transaction list.
     *
     * @param transaction The transaction to be deleted.
     */
    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransactionById(transaction.id.toLong())
        fetchTransactions()
    }

    /**
     * Saves a bank entity into the database and updates the bank list.
     *
     * @param bank The bank entity to be saved.
     */
    suspend fun saveBank(bank: BankEntity) {
        bankDao.insertBank(bank)
        fetchBanks()
    }

    /**
     * Updates a bank entity in the database and refreshes the bank list.
     *
     * @param bank The bank entity to be updated.
     */
    suspend fun updateBank(bank: BankEntity) {
        bankDao.updateBank(bank)
        fetchBanks()
    }

    /**
     * Deletes a bank entity from the database and updates the bank list.
     *
     * @param bank The bank entity to be deleted.
     */
    suspend fun deleteBank(bank: BankEntity) {
        bankDao.deleteBankById(bank.id)
        fetchBanks()
    }

    /**
     * Fetches transaction data from the database and updates [_transactions].
     */
    private fun fetchTransactions() {
        viewModelScope.launch {
            transactionDao.getAllTransactions().collect { transactions ->
                _transactions.update { transactions.map { it.toTransaction() } }
            }
        }
    }

    /**
     * Fetches bank data from the database and updates [_banks].
     */
    private fun fetchBanks() {
        viewModelScope.launch {
            bankDao.getAllBanks().collect { banks ->
                _banks.update { banks }
            }
        }
    }

    /**
     * Fetches initial data on ViewModel start-up.
     */
    fun fetchDataOnStart() {
        fetchTransactions()
        fetchBanks()
    }

}


