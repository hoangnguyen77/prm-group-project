package com.example.prm392.repository

import com.example.prm392.data.Category
import com.example.prm392.data.toCategory
import com.example.prm392.network.RetrofitInstance

class CategoryRepository {
    suspend fun fetchCategories(): List<Category> {
        // Fetch the categories response DTO from the API
        val responseDto = RetrofitInstance.categoryApi.getCategories()
        // Map the DTO items to our domain model
        return responseDto.items.map { it.toCategory() }
    }
}