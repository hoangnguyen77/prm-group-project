package com.example.prm392.data

// Domain model for Category
data class Category(
    val id: String?,
    val name: String?,
    val description: String?
)

// DTO classes for Retrofit response
data class CategoryResponseDto(
    val size: Int,
    val page: Int,
    val total: Int,
    val totalPages: Int,
    val items: List<CategoryDto>
)

// Define CategoryDto once here
data class CategoryDto(
    val id: String?,
    val name: String?,
    val description: String?,
    val createdAt: String?,
    val modifiedAt: String?
)

// Mapping extension function: Converts a DTO to the domain model
fun CategoryDto.toCategory(): Category {
    return Category(
        id = id,
        name = name,
        description = description
    )
}
