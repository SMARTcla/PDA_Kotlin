package cz.cvut.fel.sit.pda.screens.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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