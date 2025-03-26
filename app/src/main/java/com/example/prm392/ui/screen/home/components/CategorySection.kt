package com.example.prm392.ui.screen.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.prm392.data.Category
import com.example.prm392.ui.view_models.ProductViewModel

@Composable
fun CategorySection(
    category: Category,
    productViewModel: ProductViewModel,
    navController: NavController
) {
    // Category header
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = category.name ?: "No Title",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "SEE ALL",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable() {
                navController.navigate("category/${category.id}")
            }
        )
    }

    // Retrieve products using getProductsByCategory function from the ViewModel
    val products = productViewModel.getProductsByCategory(category.id ?: "")

    // Horizontal list of products
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        products.forEach { product ->
            item {
                ProductCard(product, navController = navController)
            }
        }
    }
}
