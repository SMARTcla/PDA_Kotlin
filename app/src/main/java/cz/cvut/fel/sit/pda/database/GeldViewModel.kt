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

    /*@RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: GeldEvent): Boolean {

        when (event) {
            is GeldEvent.SaveTransaction -> {
                val name = state.value.name
                val amount = state.value.amount
                val type = state.value.type
                val card = state.value.card
                val category = state.value.category

                val date = if (state.value.date.contains(".")) {
                    (SimpleDateFormat("dd.MM.yyyy").parse(state.value.date).time / 1000).toString()
                } else {
                    state.value.date
                }

                if (name.isBlank() || date.isBlank() || card.isBlank() || category.isBlank()
                ) {
                    return false
                }

                val transaction = TransactionEntity(
                    name = name,
                    amount = amount,
                    type = type,
                    date = date.toLong(),
                    card = card,
                    category = category
                )

                viewModelScope.launch {
                    transactionDao.insertTransaction(transaction)
                }
                _state.update {
                    it.copy(
                        name = "",
                        amount = 0,
                        type = TransactionType.RESTAURANT,
                        date = "",
                        card = "",
                        category = ""
                    )
                }
            }

            is GeldEvent.UpdateTransaction -> {
                val id = state.value.transactionId
                val name = state.value.name
                val amount = state.value.amount
                val type = state.value.type
                val card = state.value.card
                val category = state.value.category

                val date = if (state.value.date.contains(".")) {
                    (SimpleDateFormat("dd.MM.yyyy").parse(state.value.date).time / 1000).toString()
                } else {
                    state.value.date
                }

                val transaction = TransactionEntity(
                    id = id,
                    name = name,
                    amount = amount,
                    type = type,
                    date = date.toLong(),
                    card = card,
                    category = category
                )

                viewModelScope.launch {
                    transactionDao.insertTransaction(transaction)
                }
            }

            is GeldEvent.DeleteTransactionById -> {
                viewModelScope.launch {
                    transactionDao.deleteTransaction(event.transactionId)
                }
                _state.update {
                    it.copy(
                        transactionId = -1
                    )
                }
            }

            is GeldEvent.SaveBank -> {
                val name = state.value.name

                if (name.isBlank()
                ) {
                    return false
                }

                val bank = BankEntity(
                    name = name
                )

                viewModelScope.launch {
                    bankDao.insertBank(bank)
                }
                _state.update {
                    it.copy(
                        name = ""
                    )
                }
            }


            is GeldEvent.UpdateBank -> {
                val id = state.value.bankId
                val name = state.value.name

                val bank = BankEntity(
                    id = id,
                    name = name
                )

                viewModelScope.launch {
                    bankDao.insertBank(bank)
                }
            }
            is GeldEvent.DeleteBankById -> {
                viewModelScope.launch {
                    bankDao.deleteBank(event.bankId)
                }
                _state.update {
                    it.copy(
                        bankId = -1
                    )
                }
            }

        }
        return true
    }*/
}


