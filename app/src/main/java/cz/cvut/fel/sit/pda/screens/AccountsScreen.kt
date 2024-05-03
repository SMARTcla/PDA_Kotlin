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
import cz.cvut.fel.sit.pda.GeldScreen
import cz.cvut.fel.sit.pda.components.BasicAppBar
import cz.cvut.fel.sit.pda.components.GeldsBottomBar
import cz.cvut.fel.sit.pda.models.BankCard
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.ui.theme.DefaultColor
import cz.cvut.fel.sit.pda.ui.theme.Green
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
                canNavigateBack = false,
                onNavigateBack = {}
            )
        },
        bottomBar = {
            GeldsBottomBar(navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(GeldScreen.AddCardScreen.name) },
                backgroundColor = Green,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Account")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = false
    )
    { innerPadding ->

        Surface(color = DefaultColor,
            modifier = Modifier.padding(innerPadding) ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(accountsWithBalances) { account ->
                    AccountItem(account)
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
            Text(text = "${account.balance} CZK", style = MaterialTheme.typography.h6)
        }
    }
}