package com.example.prm392.repository

import com.example.prm392.data.AddressToCheckoutRequest
import com.example.prm392.data.UpdateOrderRequest
import com.example.prm392.network.RetrofitInstance

class PaymentRepository {
    suspend fun checkout (request: AddressToCheckoutRequest) : String {
        val response = RetrofitInstance.paymentApi.checkout(request)
        return response.payOSUrl
    }

    suspend fun updateOrderStatus(request: UpdateOrderRequest) {
        RetrofitInstance.paymentApi.updateStatusOrder(request)
    }
}
