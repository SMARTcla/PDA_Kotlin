package cz.cvut.fel.sit.pda.database

import cz.cvut.fel.sit.pda.models.BankCard
import cz.cvut.fel.sit.pda.models.Transaction

data class TransactionState(
    val transactions: List<Transaction> = emptyList(),
    val banks: List<BankEntity> = emptyList(),
    val cards: List<BankCard> = emptyList()
)