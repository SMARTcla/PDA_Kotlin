package cz.cvut.fel.sit.pda.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BudgetScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF586481)) {
        Text(text = "Hello Budget", modifier = Modifier.fillMaxSize())
    }
}