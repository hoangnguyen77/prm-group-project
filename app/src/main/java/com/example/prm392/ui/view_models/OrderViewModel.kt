package com.example.prm392.ui.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm392.data.Order
import com.example.prm392.repository.OrderRepository
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {

    // Expose order list state
    val orders = mutableStateOf<List<Order>>(emptyList())
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    private val repository = OrderRepository()

    fun fetchOrders() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                // Fetch and assign the orders list
                orders.value = repository.fetchOrders()
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading.value = false
            }
        }
    }
}