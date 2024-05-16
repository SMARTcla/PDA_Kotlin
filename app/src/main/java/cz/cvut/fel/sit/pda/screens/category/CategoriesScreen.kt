package cz.cvut.fel.sit.pda.screens.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import cz.cvut.fel.sit.pda.R
import cz.cvut.fel.sit.pda.components.BasicAppBar
import cz.cvut.fel.sit.pda.components.GeldsBottomBar
import cz.cvut.fel.sit.pda.database.TransactionType
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.ui.theme.DeepPurple500
import cz.cvut.fel.sit.pda.ui.theme.DefaultColor
import cz.cvut.fel.sit.pda.ui.theme.Indigo50

@Composable
fun CategoriesScreen(navController: NavHostController, transactions: List<Transaction>) {
    var isExpensesSelected by remember { mutableStateOf(true) }
    val filteredTransactions = transactions.filter {
        it.type.category == if (isExpensesSelected) "Expenses" else "Income"
    }
    val groupedTransactions = filteredTransactions.groupBy { it.type }
        .toSortedMap(compareBy { it.displayName })

    Scaffold(
        topBar = {
            BasicAppBar(
                title = if (isExpensesSelected) "Expenses" else "Income",
                navController = navController,
                canNavigateBack = false,
                onNavigateBack = { }
            )
        },
        bottomBar = {
            GeldsBottomBar(navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DefaultColor),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                RoundedButton(
                    isExpensesSelected = isExpensesSelected,
                    onToggle = { isExpensesSelected = !isExpensesSelected }
                )
                Spacer(modifier = Modifier.height(32.dp))

                CategoriesGrid(isExpensesSelected, TransactionType.entries, groupedTransactions)

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun RoundedButton(isExpensesSelected: Boolean, onToggle: () -> Unit) {
    Button(
        onClick = { onToggle() },
        modifier = Modifier.size(160.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = DeepPurple500,
            contentColor = Color.White
        )
    ) {
        Text(
            text = if (isExpensesSelected) "Go to Income" else "Go to Expenses",
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun CategoriesGrid(
    isExpensesSelected: Boolean,
    categories: List<TransactionType>,
    transactionSums: Map<TransactionType, List<Transaction>>
) {
    val filteredCategories =
        categories.filter { it.category == if (isExpensesSelected) "Expenses" else "Income" }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        filteredCategories.chunked(3).forEach { rowCategories ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowCategories.forEach { category ->
                    CategoryIcon(category, transactionSums[category]?.sumOf { it.amount } ?: 0L)
                }
            }
        }
    }
}


@Composable
fun CategoryIcon(category: TransactionType, sum: Long) {
    val imageRes = when (category) {
        TransactionType.RESTAURANT -> R.drawable.restaurant
        TransactionType.GROCERIES -> R.drawable.groceries
        TransactionType.TRANSPORT -> R.drawable.transport
        TransactionType.HEALTHCARE -> R.drawable.health
        TransactionType.LEISURE -> R.drawable.leisure
        TransactionType.GIFTS -> R.drawable.gifts
        TransactionType.SHOPPING -> R.drawable.shopping
        TransactionType.FAMILY -> R.drawable.family
        TransactionType.OTHER_EXP -> R.drawable.other
        TransactionType.SALARY -> R.drawable.salary
        TransactionType.BENEFITS -> R.drawable.benefits
        TransactionType.OTHER_INC -> R.drawable.other

    }
    val iconPainter: Painter = painterResource(id = imageRes)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(10.dp)
    ) {
        Image(
            painter = iconPainter,
            contentDescription = category.displayName,
            modifier = Modifier.size(66.dp)
        )

        Text(
            text = category.displayName,
            modifier = Modifier.padding(top = 2.dp, bottom = 2.dp),
            style = TextStyle(color = Indigo50, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        )

        Text(
            text = "$sum CZK",
            style = TextStyle(color = Indigo50, fontSize = 12.sp)
        )
    }
}