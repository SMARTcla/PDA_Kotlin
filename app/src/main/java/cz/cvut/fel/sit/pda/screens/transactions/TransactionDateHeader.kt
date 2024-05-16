package cz.cvut.fel.sit.pda.screens.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cz.cvut.fel.sit.pda.ui.theme.Blue50
import cz.cvut.fel.sit.pda.ui.theme.DeepPurple500
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TransactionDateHeader(date: LocalDate, totalAmount: Long) {
    val dayOfMonthFormatter = DateTimeFormatter.ofPattern("dd")
    val monthYearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
    val dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .background(DeepPurple500, RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text(
                text = date.format(dayOfMonthFormatter),
                style = MaterialTheme.typography.headlineSmall,
                color = Blue50
            )
            Column(
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Text(
                    text = date.format(dayOfWeekFormatter),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Blue50
                )
                Text(
                    text = date.format(monthYearFormatter),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Blue50
                )
            }
        }
        Text(
            text = "$totalAmount CZK",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            color = Color.White
        )
    }
}