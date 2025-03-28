package com.example.prm392.repository

import com.example.prm392.data.CartItem
import com.example.prm392.data.CartUpdateRequest
import com.example.prm392.data.toCartItem
import com.example.prm392.network.RetrofitInstance

class CartRepository {
    suspend fun fetchCartItems(): List<CartItem> {
        // Fetch the list of CartItemDto from the API
        val cartDtoList = RetrofitInstance.cartApi.getCartItems()
        // Map each DTO to a domain model and return the list
        return cartDtoList.map { it.toCartItem() }
    }
    // POST/PATCH: Add a product to the cart.
    // (Using PATCH endpoint as defined in your API)
    suspend fun addProductToCart(productId: String, quantity: Int): List<CartItem> {
        val request = CartUpdateRequest(productId, quantity)
        val cartDtoList = RetrofitInstance.cartApi.addProductToCart(request)
        return cartDtoList.map { it.toCartItem() }
    }

    // PATCH: Update the quantity of an existing product in the cart.
    suspend fun updateProductQuantity(productId: String, quantity: Int): List<CartItem> {
        val request = CartUpdateRequest(productId, quantity)
        val cartDtoList = RetrofitInstance.cartApi.updateProductQuantity(request)
        return cartDtoList.map { it.toCartItem() }
    }

    // DELETE: Remove a specific product from the cart.
    suspend fun deleteProductFromCart(productId: String): List<CartItem> {
        val cartDtoList = RetrofitInstance.cartApi.deleteProductFromCart(productId)
        return cartDtoList.map { it.toCartItem() }
    }
}