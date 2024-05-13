package cz.cvut.fel.sit.pda.screens.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import cz.cvut.fel.sit.pda.components.BasicAppBar
import cz.cvut.fel.sit.pda.components.GeldsBottomBar
import cz.cvut.fel.sit.pda.models.Transaction
import cz.cvut.fel.sit.pda.models.TransactionType
import cz.cvut.fel.sit.pda.ui.theme.DeepPurple500
import cz.cvut.fel.sit.pda.ui.theme.DefaultColor
import cz.cvut.fel.sit.pda.R
import cz.cvut.fel.sit.pda.ui.theme.Indigo50

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(navController: NavHostController, transactions: MutableList<Transaction>) {
    var isExpensesSelected by remember { mutableStateOf(true) }
    // Filter and group transactions by type
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

                // Use the custom RoundedButton here
                RoundedButton(
                    isExpensesSelected = isExpensesSelected,
                    onToggle = { isExpensesSelected = !isExpensesSelected }
                )
                Spacer(modifier = Modifier.height(32.dp))

                // Display categories around the button
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
        modifier = Modifier.size(160.dp), // Задаем размер кнопки, чтобы она была круглой
        shape = CircleShape, // Задаем форму круга
        colors = ButtonDefaults.buttonColors(
            containerColor = DeepPurple500, // Фон кнопки
            contentColor = Color.White // Цвет текста
        )
    ) {
        Text(
            text = if (isExpensesSelected) "Go to Income" else "Go to Expenses",
            style = TextStyle(
                color = Color.White, // Цвет текста
                fontSize = 16.sp, // Размер шрифта
                fontWeight = FontWeight.Bold // Жирность шрифта
            )
        )
    }
}

@Composable
fun CategoriesGrid(isExpensesSelected: Boolean, categories: List<TransactionType>, transactionSums: Map<TransactionType, List<Transaction>>) {
    val filteredCategories = categories.filter { it.category == if (isExpensesSelected) "Expenses" else "Income" }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        filteredCategories.chunked(3).forEach { rowCategories ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), // Добавляет отступы слева и справа
                horizontalArrangement = Arrangement.SpaceBetween // Элементы будут равномерно распределены по горизонтали
            ) {
                rowCategories.forEach { category ->
                    CategoryIcon(category, transactionSums[category]?.sumOf { it.amount } ?: 0.0)
                }
            }
        }
    }
}


@Composable
fun CategoryIcon(category: TransactionType, sum: Double) {
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

        else -> R.drawable.main // Use a default icon
    }
    val iconPainter: Painter = painterResource(id = imageRes)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(20.dp)
    // padding around each icon for better visual separation
    ) {
        Image(painter = iconPainter,
            contentDescription = category.displayName,
            modifier = Modifier.size(70.dp)
        ) // Icon size

        Text(text = category.displayName,
            modifier = Modifier.padding(top = 2.dp, bottom = 2.dp),
            style = TextStyle(color = Indigo50, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        )

        Text(text = "$sum CZK",
            style = TextStyle(color = Indigo50, fontSize = 12.sp)
        )
    }
}