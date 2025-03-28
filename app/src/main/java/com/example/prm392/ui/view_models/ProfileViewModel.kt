package com.example.prm392.ui.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm392.data.UpdateUserInformationRequest
import com.example.prm392.data.UserInformation
import com.example.prm392.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    // Expose user profile information state
    val userInformation = mutableStateOf<UserInformation?>(null)
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    // StateFlow to track update status
    private val _updateStatus = MutableStateFlow<Boolean?>(null)
    val updateStatus: StateFlow<Boolean?> = _updateStatus.asStateFlow()

    private val repository = ProfileRepository()

    // Fetch user information from the API
    fun fetchUserInformation() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                userInformation.value = repository.fetchUserInformation()
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage ?: "An error occurred"
            } finally {
                isLoading.value = false
            }
        }
    }

    // Update user information using a request object
    fun updateUserInformation(request: UpdateUserInformationRequest) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                repository.updateUserInformation(request)
                _updateStatus.value = true // Indicate success
                errorMessage.value = null
            } catch (e: Exception) {
                _updateStatus.value = false // Indicate failure
                errorMessage.value = e.localizedMessage ?: "Failed to update information"
            } finally {
                // Refetch user information after update
                fetchUserInformation()
                isLoading.value = false
            }
        }
    }

    // Reset the update status after UI processing
    fun resetUpdateStatus() {
        _updateStatus.value = null
    }
}