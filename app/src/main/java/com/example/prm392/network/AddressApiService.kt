package com.example.prm392.network

import com.example.prm392.data.District
import com.example.prm392.data.Province
import retrofit2.http.GET
import retrofit2.http.Query

interface AddressApiService {

    @GET("province")
    suspend fun getProvinces(): Any

    @GET("district")
    suspend fun getDistricts(
        @Query("idProvince") provinceId: String
    ): Any

    @GET("commune")
    suspend fun getWards(
        @Query("idDistrict") districtId: String
    ): Any
}