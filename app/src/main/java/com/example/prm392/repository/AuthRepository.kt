package com.example.prm392.repository

import com.example.prm392.data.LoginRequest
import com.example.prm392.data.UserModel
import com.example.prm392.network.RetrofitInstance

class AuthRepository {
    suspend fun login(usernameOrPhoneNumber: String, password: String): UserModel {
        // Create the login request object
        val request = LoginRequest(usernameOrPhoneNumber, password)
        // Make the API call via Retrofit and return the UserModel
        return RetrofitInstance.authApi.login(request)
    }
}
