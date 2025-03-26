package com.example.prm392.ui.screen.home.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.prm392.ui.view_models.CategoryViewModel
import com.example.prm392.ui.view_models.ProductViewModel

@Composable
fun HomeContent(
    categoryViewModel: CategoryViewModel,
    productViewModel: ProductViewModel,
    navController: NavHostController
) {

    val categories = categoryViewModel.categories.value

    LazyColumn {
        categories.forEach { cate ->
            item {
                CategorySection(
                    category = cate,
                    productViewModel = productViewModel,
                    navController = navController
                )
            }
        }
    }
}