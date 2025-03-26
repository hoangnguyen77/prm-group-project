package com.example.prm392.ui.screen.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CartScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Text("Cart Screen", style = MaterialTheme.typography.headlineMedium)
    }
}