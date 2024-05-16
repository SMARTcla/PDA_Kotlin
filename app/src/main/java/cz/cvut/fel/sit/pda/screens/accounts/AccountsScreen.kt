package cz.cvut.fel.sit.pda.screens.accounts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.GeldScreen
import cz.cvut.fel.sit.pda.components.BasicAppBar
import cz.cvut.fel.sit.pda.components.GeldsBottomBar
import cz.cvut.fel.sit.pda.database.TransactionType
import cz.cvut.fel.sit.pda.models.BankCard
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.ui.theme.DeepPurple500
import cz.cvut.fel.sit.pda.ui.theme.DefaultColor
import cz.cvut.fel.sit.pda.ui.theme.Green700
import cz.cvut.fel.sit.pda.ui.theme.Pink800

@Composable
fun AccountsScreen(
    navController: NavHostController,
    transactions: List<Transaction>,
    banks: List<BankCard>
) {
    val accountsWithBalances = remember {
        mutableStateOf(listOf<BankCardWithBalance>())
    }

    LaunchedEffect(transactions, banks) {
        val balances = transactions.groupBy { it.cardName }.mapValues { (_, trans) ->
            trans.sumOf { if (it.type == TransactionType.SALARY || it.type == TransactionType.BENEFITS) it.amount else -it.amount }
        }
        val banksBalances = mutableListOf<BankCardWithBalance>()

        banks.forEach { bank ->
            val balance = balances.getOrElse(bank.name) { 0L }
            banksBalances.add(BankCardWithBalance(bank.name, balance))
        }
        accountsWithBalances.value = banksBalances
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
                containerColor = DeepPurple500,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Account")
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    )
    { innerPadding ->

        Surface(
            color = DefaultColor,
            modifier = Modifier.padding(innerPadding)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(accountsWithBalances.value) { account ->
                    AccountItem(account, navController)
                }
            }
        }
    }
}

data class BankCardWithBalance(val name: String, val balance: Long)

@Composable
fun AccountItem(account: BankCardWithBalance, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .clickable { navController.navigate("cardDetails/${account.name}") },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = account.name, style = MaterialTheme.typography.headlineSmall)
            val balanceTextColor = if (account.balance >= 0) Green700 else Pink800
            Text(
                text = "${account.balance} CZK",
                style = MaterialTheme.typography.headlineSmall,
                color = balanceTextColor
            )
        }
    }
}

fun deleteCard(card: BankCard, cardsList: MutableList<BankCard>) {
    cardsList.remove(card)
}

