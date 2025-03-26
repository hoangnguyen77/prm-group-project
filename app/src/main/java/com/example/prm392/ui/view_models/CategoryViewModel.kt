package com.example.prm392.ui.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm392.data.Category
import com.example.prm392.repository.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    // Expose category list state
    val categories = mutableStateOf<List<Category>>(emptyList())
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    private val repository = CategoryRepository()

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                // Fetch and assign the categories list
                val categoryList = repository.fetchCategories()
                categories.value = categoryList
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun getCategoryById(categoryId: String): Category? {
        return categories.value.find { it.id == categoryId }
    }
}