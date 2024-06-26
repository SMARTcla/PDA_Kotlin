package cz.cvut.fel.sit.pda.screens.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.cvut.fel.sit.pda.R
import cz.cvut.fel.sit.pda.database.TransactionType
import cz.cvut.fel.sit.pda.ui.theme.Indigo50

/**
 * Composable function for displaying a category icon.
 *
 * @param category The transaction category.
 * @param sum The total sum of transactions for the category.
 * @param onClick Callback function for when the icon is clicked.
 */
@Composable
fun CategoryIcon(category: TransactionType, sum: Long, onClick: () -> Unit) {
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
        TransactionType.PROFIT -> R.drawable.other
    }
    val iconPainter: Painter = painterResource(id = imageRes)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(10.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = iconPainter,
            contentDescription = category.displayName,
            modifier = Modifier
                .size(66.dp)
                .border(width = 2.dp, color = Indigo50, shape = CircleShape),
        )
        Spacer(Modifier.height(4.dp))

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
