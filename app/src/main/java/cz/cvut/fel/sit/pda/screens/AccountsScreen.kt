package cz.cvut.fel.sit.pda.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.components.BasicAppBar
import cz.cvut.fel.sit.pda.components.GeldsBottomBar
import cz.cvut.fel.sit.pda.models.BankCard
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.utils.TemporaryDatabase

@Composable
fun AccountsScreen(navController: NavHostController, transactions: MutableList<Transaction>) {
    val accountsWithBalances = remember { mutableStateListOf<BankCardWithBalance>() }

    LaunchedEffect(transactions) {
        val balances = transactions.groupBy { it.cardName }.mapValues { (_, trans) ->
            trans.sumOf { it.amount }
        }

        TemporaryDatabase.bankCards.forEach { card ->
            val balance = balances[card.name] ?: 0.0
            accountsWithBalances.add(BankCardWithBalance(card.name, balance))
        }
    }

    Scaffold (
        topBar = {
            BasicAppBar(
                title = "Accounts",
                navController = navController,
                canNavigateBack = false, // Пример: нет кнопки назад
                onNavigateBack = {}
            )
        },
        bottomBar = {
            GeldsBottomBar(navController)
        }
    )
    { innerPadding ->


        Surface(color = Color(0xFF586481), modifier = Modifier.padding(innerPadding) ) {
            Column(modifier = Modifier.fillMaxSize()) {
//                TopAppBar(title = { Text("Accounts999") }, backgroundColor = Color(0xFF586481))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                ) {
                    items(accountsWithBalances) { account ->
                        AccountItem(account)
                    }
                }
                FloatingActionButton(
                    onClick = { /* Handle add account */ },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.End),
                    backgroundColor = Color.Yellow
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Account")
                }
            }
        }
    }
}

data class BankCardWithBalance(val name: String, val balance: Double)

@Composable
fun AccountItem(account: BankCardWithBalance) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = account.name, style = MaterialTheme.typography.h6)
            Text(text = "${account.balance} Kč", style = MaterialTheme.typography.h6)
        }
    }
}