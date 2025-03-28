package com.example.prm392.network

import com.example.prm392.data.CartItemDto
import com.example.prm392.data.CartUpdateRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CartApiService {
    @GET("api/v1/carts")
    suspend fun getCartItems(): List<CartItemDto>
    @POST("api/v1/carts")
    suspend fun addProductToCart(
        @Body request: CartUpdateRequest
    ): List<CartItemDto>

    @PATCH("api/v1/carts")
    suspend fun updateProductQuantity(
        @Body request: CartUpdateRequest
    ): List<CartItemDto>

    @DELETE("api/v1/products/{id}/cart")
    suspend fun deleteProductFromCart(
        @Path("id") productId: String
    ): List<CartItemDto>
}