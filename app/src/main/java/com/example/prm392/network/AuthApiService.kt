package com.example.prm392.network

import com.example.prm392.data.LoginRequest
import com.example.prm392.data.UserModel
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApiService {
    @Headers(
        "accept: text/plain",
        "Content-Type: application/json"
    )
    @POST("api/v1/login")
    suspend fun login(@Body request: LoginRequest): UserModel
}