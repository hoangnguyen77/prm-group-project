package com.example.prm392.repository

import com.example.prm392.data.UpdateUserInformationRequest
import com.example.prm392.data.UserInformation
import com.example.prm392.data.toUserInformation
import com.example.prm392.network.RetrofitInstance

class ProfileRepository {
    suspend fun fetchUserInformation(): UserInformation {
        val dto = RetrofitInstance.profileApi.getUserInformation()
        return dto.toUserInformation()
    }

    suspend fun updateUserInformation(request: UpdateUserInformationRequest) {
        RetrofitInstance.profileApi.updateUserInformation(request)
    }
}