package cz.cvut.fel.sit.pda.utils

import cz.cvut.fel.sit.pda.models.BankCard

object TemporaryDatabase {
    val bankCards = mutableListOf(
        BankCard("Raiffeisen"),
        BankCard("Morgan"),
        BankCard("CSOB")
    )
}