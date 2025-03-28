package com.example.prm392.data

data class UserInformation(
    val id: String,
    val userId: String,
    val username: String,
    val phoneNumber: String,
    val fullName: String,
    val commune: String,
    val district: String,
    val province: String,
    val address: String,
    val provinceCode: String,
    val districtCode: String,
    val communeCode: String
)


data class UserInformationDto(
    val id: String,
    val userId: String,
    val username: String,
    val phoneNumber: String,
    val fullName: String,
    val commune: String,
    val district: String,
    val province: String,
    val address: String,
    val provinceCode: String,
    val districtCode: String,
    val communeCode: String
)

// Mapping extension function to convert DTO to domain model
fun UserInformationDto.toUserInformation(): UserInformation {
    return UserInformation(
        id = id,
        userId = userId,
        username = username,
        phoneNumber = phoneNumber,
        fullName = fullName,
        commune = commune,
        district = district,
        province = province,
        address = address,
        provinceCode = provinceCode,
        districtCode = districtCode,
        communeCode = communeCode
    )
}

// Request model for updating user information (PATCH)
data class UpdateUserInformationRequest(
    val phoneNumber: String,
    val fullName: String,
    val commune: String,
    val district: String,
    val province: String,
    val address: String,
    val provinceCode: String,
    val districtCode: String,
    val communeCode: String
)