package com.example.prm392.data


data class Order(
    val id: String,
    val total: Double,
    val code: String,
    val address: String,
    val status: Int,
    val createdAt: String,
    val modifiedAt: String
)

// DTO representing a single order item returned from the API
data class OrderDto(
    val id: String,
    val total: Double,
    val code: String,
    val address: String,
    val status: Int,
    val createdAt: String,
    val modifiedAt: String,
    // The "member" field is ignored since we don't need it
)

// DTO for the overall orders response
data class OrderResponseDto(
    val size: Int,
    val page: Int,
    val total: Int,
    val totalPages: Int,
    val items: List<OrderDto>
)

// Mapping extension function to convert an OrderDto to a domain model Order
fun OrderDto.toOrder(): Order {
    return Order(
        id = id,
        total = total,
        code = code,
        address = address,
        status = status,
        createdAt = createdAt,
        modifiedAt = modifiedAt
    )
}


data class UpdateOrderRequest(
    val orderCode: String,
)