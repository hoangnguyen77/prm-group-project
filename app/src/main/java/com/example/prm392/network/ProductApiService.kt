package com.example.prm392.network

import com.example.prm392.data.ProductResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApiService {
    @GET("api/v1/products")
    suspend fun getProducts(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 200
    ): ProductResponseDto
}