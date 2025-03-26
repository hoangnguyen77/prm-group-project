package com.example.prm392.network

import com.example.prm392.data.CategoryResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryApiService {
    @GET("api/v1/categories")
    suspend fun getCategories(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 45
    ): CategoryResponseDto
}