package com.example.prm392.data

// Our domain model remains the same
data class Product(
    val id: String?,
    val name: String?,
    val description: String?,
    val quantity: Int?,
    val price: Double?,
    val isKit: Boolean?,
    val categoryIds: List<String>?,
    val productImages: List<ProductImage>?
)

data class ProductImage(
    val id: String?,
    val imageUrl: String?,
    val isMain: Boolean?
)

// DTO classes for Retrofit response
data class ProductResponseDto(
    val size: Int,
    val page: Int,
    val total: Int,
    val totalPages: Int,
    val items: List<ProductDto>
)

data class ProductDto(
    val id: String?,
    val name: String?,
    val description: String?,
    val quantity: Int?,
    val price: Double?,
    val isKit: Boolean?,
    // Re-use CategoryDto defined in CategoryModels.kt
    val categories: List<CategoryDto>?,
    val productImages: List<ProductImage>?
)

// Extension function to map ProductDto to Product
fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        name = name,
        description = description,
        quantity = quantity,
        price = price,
        isKit = isKit,
        // Extract category IDs from the categories list
        categoryIds = categories?.mapNotNull { it.id },
        productImages = productImages
    )
}
