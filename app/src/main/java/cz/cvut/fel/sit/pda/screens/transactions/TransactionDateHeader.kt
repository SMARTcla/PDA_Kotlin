package cz.cvut.fel.sit.pda.screens.transactions

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionDateHeader(date: LocalDate, totalAmount: Double) {
    val dayOfMonthFormatter = DateTimeFormatter.ofPattern("dd")
    val monthYearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
    val dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFF69789A)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = date.format(dayOfMonthFormatter),
                style = MaterialTheme.typography.h4,
                color = Color.White
            )
            Column(
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Text(
                    text = date.format(dayOfWeekFormatter),
                    style = MaterialTheme.typography.body2,
                    color = Color.White
                )
                Text(
                    text = date.format(monthYearFormatter),
                    style = MaterialTheme.typography.body2,
                    color = Color.White
                )
            }
        }
        Text(
            text = "${totalAmount} CZK",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.align(Alignment.CenterVertically),
            color = Color.White
        )
    }
}
