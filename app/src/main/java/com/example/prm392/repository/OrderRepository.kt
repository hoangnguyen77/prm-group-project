package com.example.prm392.repository

import com.example.prm392.data.Order
import com.example.prm392.data.toOrder
import com.example.prm392.network.RetrofitInstance

class OrderRepository {
    suspend fun fetchOrders(): List<Order> {
        // Call the API service to fetch orders
        val responseDto = RetrofitInstance.orderApi.getOrders()
        // Map each OrderDto to our domain model Order
        return responseDto.items.map { it.toOrder() }
    }
}