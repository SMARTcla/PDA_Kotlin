package cz.cvut.fel.sit.pda.screens.category

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.cvut.fel.sit.pda.ui.theme.*

const val TOTAL_EXPENSES = "Total Expenses:\n"
const val TOTAL_INCOME = "Total Income:\n"
const val CURRENCY = " CZK\n\n"
const val GO_TO_INCOME = "Go to Income"
const val GO_TO_EXPENSES = "Go to Expenses"

@Composable
fun RoundedButton(isExpensesSelected: Boolean, totalAmount: Long, onToggle: () -> Unit) {
    val expensesText = buildAnnotatedString {
        append(TOTAL_EXPENSES)
        pushStyle(SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
        append("$totalAmount")
        pop()
        append(CURRENCY)
        pushStyle(SpanStyle(color = Color.Yellow))
        append(GO_TO_INCOME)
    }

    val incomeText = buildAnnotatedString {
        append(TOTAL_INCOME)
        pushStyle(SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
        append("$totalAmount")
        pop()
        append(CURRENCY)
        pushStyle(SpanStyle(color = Color.Yellow))
        append(GO_TO_EXPENSES)
    }

    Button(
        onClick = { onToggle() },
        modifier = Modifier
            .size(180.dp)
            .border(width = 2.dp, color = Indigo50, shape = CircleShape),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = DeepPurple500,
            contentColor = Indigo50
        )
    ) {
        Text(
            text = if (isExpensesSelected) expensesText else incomeText,
            style = TextStyle(
                color = Indigo50,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
