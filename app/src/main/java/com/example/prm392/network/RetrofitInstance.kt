package com.example.prm392.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://production-api.stemlabs.store/"

    // Hold the token here; update it after login
    private var token: String? = null

    // Function to update the token after a successful login
    fun updateToken(newToken: String?) {
        token = newToken
    }

    // OkHttpClient that attaches the token via the AuthInterceptor
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor { token })
        .build()

    // Shared Retrofit instance using the OkHttpClient
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Use the custom OkHttpClient
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API service instances
    val api: ProductApiService by lazy {
        retrofit.create(ProductApiService::class.java)
    }

    val categoryApi: CategoryApiService by lazy {
        retrofit.create(CategoryApiService::class.java)
    }

    val authApi: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }
}