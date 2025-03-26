package com.example.prm392.data

data class UserModel(
    val id: String,
    val username: String,
    val phoneNumber: String,
    val fullName: String,
    val token: String,
    val refreshToken: String
)

data class LoginRequest(
    val usernameOrPhoneNumber: String,
    val password: String
)