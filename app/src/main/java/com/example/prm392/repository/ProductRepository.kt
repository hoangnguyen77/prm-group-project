package com.example.prm392.repository

import com.example.prm392.data.Product
import com.example.prm392.data.toProduct
import com.example.prm392.network.RetrofitInstance

class ProductRepository {
    suspend fun fetchProducts(): List<Product> {
        // Call the API service
        val responseDto = RetrofitInstance.api.getProducts()
        // Map the DTO items to domain models
        return responseDto.items.map { it.toProduct() }
    }
}