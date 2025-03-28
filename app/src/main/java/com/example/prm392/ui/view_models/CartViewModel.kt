package com.example.prm392.ui.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm392.data.AddressToCheckoutRequest
import com.example.prm392.data.CartItem
import com.example.prm392.data.UpdateOrderRequest
import com.example.prm392.repository.CartRepository
import com.example.prm392.repository.PaymentRepository
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    // Expose cart list state
    val cartItems = mutableStateOf<List<CartItem>>(emptyList())
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val paymentUrl = mutableStateOf<String?>(null)

    private val repository = CartRepository()
    private val paymentRepository = PaymentRepository()


    // Modified checkout function
    fun checkout(request: AddressToCheckoutRequest) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                // Call the payment repository to process the checkout
                val url = paymentRepository.checkout(request)
                paymentUrl.value = url // Set the state
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage ?: "Failed to checkout"
                paymentUrl.value = null // Reset on error
            } finally {
                isLoading.value = false
            }
        }
    }

    fun updateOrderStatus(request : UpdateOrderRequest) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                // Call the payment repository to process the checkout
                paymentRepository.updateOrderStatus(request)
                paymentUrl.value = null // Set the state
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage ?: "Failed to checkout"
                paymentUrl.value = null // Reset on error
            } finally {
                isLoading.value = false
            }
        }
    }

    fun fetchCartItems() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                // Fetch and assign the cart items list
                val items = repository.fetchCartItems()
                cartItems.value = items
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading.value = false
            }
        }
    }

    // Add a product to the cart
    fun addToCart(productId: String) {
        viewModelScope.launch {
            isLoading.value = true
            //if the product is already in the cart, update the quantity
            if (cartItems.value.any { it.productId == productId }) {
                val newQuantity = cartItems.value.find { it.productId == productId }!!.quantity + 1
                updateCartQuantity(productId, newQuantity)
                return@launch
            }
            try {
                val updatedList = repository.addProductToCart(productId, 1)
                cartItems.value = updatedList
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage ?: "Failed to add product to cart"
            } finally {
                isLoading.value = false
            }
        }
    }

    // Update the quantity of a product in the cart
    fun updateCartQuantity(productId: String, quantity: Int) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val updatedList = repository.updateProductQuantity(productId, quantity)
                cartItems.value = updatedList
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage ?: "Failed to update cart quantity"
            } finally {
                isLoading.value = false
            }
        }
    }

    // Delete a product from the cart
    fun deleteFromCart(productId: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val updatedList = repository.deleteProductFromCart(productId)
                cartItems.value = updatedList
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage ?: "Failed to delete product from cart"
            } finally {
                isLoading.value = false
            }
        }
    }
}