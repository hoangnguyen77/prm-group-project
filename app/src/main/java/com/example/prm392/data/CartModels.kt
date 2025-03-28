package com.example.prm392.data

data class CartItem(
    val productId: String,
    val name: String,
    val description: String,
    val price: Int,
    val quantity: Int,
    val mainImage: String,
    val productQuantity: Int
)

// DTO matching the API response
data class CartItemDto(
    val productId: String,
    val name: String,
    val description: String,
    val price: Int,
    val quantity: Int,
    val mainImage: String,
    val productQuantity: Int
)

// Extension function to map DTO to domain model
fun CartItemDto.toCartItem(): CartItem {
    return CartItem(
        productId = productId,
        name = name,
        description = description,
        price = price,
        quantity = quantity,
        mainImage = mainImage,
        productQuantity = productQuantity
    )
}

data class CartUpdateRequest(
    val productId: String,
    val quantity: Int
)

data class PaymentUrlResponse(
    val payOSUrl: String
)