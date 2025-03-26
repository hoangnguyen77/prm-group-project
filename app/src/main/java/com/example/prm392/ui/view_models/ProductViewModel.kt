package com.example.prm392.ui.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm392.data.Product
import com.example.prm392.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    // Expose product list state
    val products = mutableStateOf<List<Product>>(emptyList())
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    private val repository = ProductRepository()

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val productList = repository.fetchProducts()
                products.value = productList
                errorMessage.value = null
            } catch (e: Exception) {
                // Handle any errors (network, parsing, etc.)
                errorMessage.value = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading.value = false
            }
        }
    }
    fun getProductsByCategory(categoryId: String): List<Product> {
        return products.value.filter { product ->
            product.categoryIds?.contains(categoryId) == true
        }
    }
    fun getProductById(productId: String): Product? {
        return products.value.find { it.id == productId }
    }
}