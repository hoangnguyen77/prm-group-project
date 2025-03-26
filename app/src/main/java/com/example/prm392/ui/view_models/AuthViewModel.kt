package com.example.prm392.ui.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm392.data.UserModel
import com.example.prm392.network.RetrofitInstance
import com.example.prm392.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    // Holds the logged-in user's data (or null if not logged in)
    val userModel = mutableStateOf<UserModel?>(null)
    // Loading state for the login process
    val isLoading = mutableStateOf(false)
    // Error message if login fails
    val errorMessage = mutableStateOf<String?>(null)

    private val repository = AuthRepository()

    /**
     * Initiates the login process.
     * On success, updates userModel and attaches the token for future API calls.
     * On failure, updates errorMessage.
     */
    fun login(usernameOrPhoneNumber: String, password: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                // Perform the login API call via the repository
                val user = repository.login(usernameOrPhoneNumber, password)
                // Attach the token for subsequent API calls
                RetrofitInstance.updateToken(user.token)
                // Update state with logged-in user
                userModel.value = user
                errorMessage.value = null
            } catch (e: Exception) {
                // Handle error and update state
                errorMessage.value = e.localizedMessage ?: "An error occurred"
                userModel.value = null
            } finally {
                isLoading.value = false
            }
        }
    }
    fun logout() {
        userModel.value = null
        RetrofitInstance.updateToken(null)
    }
}