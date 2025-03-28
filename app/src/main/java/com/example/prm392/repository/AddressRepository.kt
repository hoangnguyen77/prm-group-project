package com.example.prm392.repository

import com.example.prm392.data.District
import com.example.prm392.data.Province
import com.example.prm392.data.Ward
import com.example.prm392.network.AddressRetrofitInstance

class AddressRepository {
    suspend fun fetchProvinces(): List<Province> {
        val response = AddressRetrofitInstance.api.getProvinces()
        if (response is List<*>) {
            return response.mapNotNull { item ->
                (item as? Map<*, *>)?.let { map ->
                    Province(
                        idProvince = map["idProvince"] as? String ?: "",
                        name = map["name"] as? String ?: ""
                    )
                }
            }
        } else {
            throw Exception("Failed to load provinces")
        }
    }

    suspend fun fetchDistricts(provinceId: String): List<District> {
        val response = AddressRetrofitInstance.api.getDistricts(provinceId)
        if (response is List<*>) {
            return response.mapNotNull { item ->
                (item as? Map<*, *>)?.let { map ->
                    District(
                        idDistrict = map["idDistrict"] as? String ?: "",
                        name = map["name"] as? String ?: ""
                    )
                }
            }
        } else {
            throw Exception("Failed to load districts")
        }
    }

    suspend fun fetchWards(districtId: String): List<Ward> {
        val response = AddressRetrofitInstance.api.getWards(districtId)
        if (response is List<*>) {
            return response.mapNotNull { item ->
                (item as? Map<*, *>)?.let { map ->
                    Ward(
                        idCommune = map["idCommune"] as? String ?: "",
                        name = map["name"] as? String ?: ""
                    )
                }
            }
        } else {
            throw Exception("Failed to load wards")
        }
    }
}