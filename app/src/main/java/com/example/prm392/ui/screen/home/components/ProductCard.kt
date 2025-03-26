package com.example.prm392.ui.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.prm392.data.Product

@Composable
fun ProductCard(
    product: Product,
    navController: NavController
) {
    // Select main image if available, otherwise fallback to first image, or empty string.
    val imageUrl = product.productImages?.firstOrNull { it.isMain == true }?.imageUrl
        ?: product.productImages?.firstOrNull()?.imageUrl
        ?: ""

    Card(
        modifier = Modifier
            .width(150.dp)
            .padding(end = 8.dp)
            .clickable {
                product.id?.let { id ->
                    navController.navigate("productDetail/$id")
                }
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            // Display product image using AsyncImage
            AsyncImage(
                model = imageUrl,
                contentDescription = product.name ?: "Product Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Display product name
            Text(
                text = product.name ?: "Unknown Product",
                style = MaterialTheme.typography.bodyMedium,
                minLines = 2,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Display product price with a fallback for null values
            Text(
                text = "${product.price ?: 0.0} VND",
                style = MaterialTheme.typography.labelLarge,
                color = Color(0xFF008577)
            )
        }
    }
}

