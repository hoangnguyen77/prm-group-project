package com.example.prm392.network

import com.example.prm392.data.OrderResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OrderApiService {
    @GET("api/v1/orders")
    suspend fun getOrders(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 30
    ): OrderResponseDto
}