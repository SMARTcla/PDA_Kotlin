package cz.cvut.fel.sit.pda.database

import cz.cvut.fel.sit.pda.models.Transaction

/**
 * Data class representing the state of transactions and banks.
 *
 * @property transactions List of transactions.
 * @property banks List of bank entities.
 */
data class TransactionState(
    val transactions: List<Transaction> = emptyList(),
    val banks: List<BankEntity> = emptyList()
)