package com.example.prm392.network

import com.example.prm392.data.UpdateUserInformationRequest
import com.example.prm392.data.UserInformationDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface ProfileApiService {
    @GET("api/v1/members/information")
    suspend fun getUserInformation(): UserInformationDto

    @PATCH("api/v1/members")
    suspend fun updateUserInformation(
        @Body request: UpdateUserInformationRequest
    )
}