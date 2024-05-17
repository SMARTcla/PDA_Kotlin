package cz.cvut.fel.sit.pda.screens.category

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