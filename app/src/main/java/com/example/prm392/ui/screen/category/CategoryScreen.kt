package com.example.prm392.ui.screen.category

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.prm392.ui.screen.home.components.ProductCard
import com.example.prm392.ui.view_models.CategoryViewModel
import com.example.prm392.ui.view_models.ProductViewModel

@Composable
fun CategoryScreen(
    categoryId: String,
    productViewModel: ProductViewModel,
    categoryViewModel: CategoryViewModel,
    navController: NavController
) {
    // Get the list of products for this category
    val products = productViewModel.getProductsByCategory(categoryId)
    val category = categoryViewModel.getCategoryById(categoryId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header: Category Title and "SEE ALL"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = category?.name ?: "No Title",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "SEE ALL",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2-Column Grid of Products
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products) { product ->
                // Use your existing ProductCard or a custom card layout
                ProductCard(product, navController = navController)
            }
        }
    }
}
