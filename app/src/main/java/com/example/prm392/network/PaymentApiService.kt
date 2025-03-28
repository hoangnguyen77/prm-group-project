package com.example.prm392.network

import com.example.prm392.data.AddressToCheckoutRequest
import com.example.prm392.data.PaymentUrlResponse
import com.example.prm392.data.UpdateOrderRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST

interface PaymentApiService {
    @POST("api/v1/payments/checkout")
    @Headers("Accept: text/plain")
    suspend fun checkout(@Body request: AddressToCheckoutRequest): PaymentUrlResponse

    @PATCH("api/v1/payments")
    suspend fun updateStatusOrder(
        @Body request : UpdateOrderRequest
    )
}