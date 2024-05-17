package cz.cvut.fel.sit.pda.screens.category

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.cvut.fel.sit.pda.ui.theme.DeepPurple500
import cz.cvut.fel.sit.pda.ui.theme.Indigo50

@Composable
fun RoundedButton(isExpensesSelected: Boolean, totalAmount: Long, onToggle: () -> Unit) {
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
            text = if (isExpensesSelected) "Total: " +
                    "$totalAmount CZK\n\nGo to Income" else "Total: $totalAmount CZK\n\nGo to Expenses",
            style = TextStyle(
                color = Indigo50,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
