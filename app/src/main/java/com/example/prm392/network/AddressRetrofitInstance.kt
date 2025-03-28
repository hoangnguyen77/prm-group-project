package com.example.prm392.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AddressRetrofitInstance {
    private const val BASE_URL = "https://vietnam-administrative-division-json-server-swart.vercel.app/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: AddressApiService by lazy {
        retrofit.create(AddressApiService::class.java)
    }
}