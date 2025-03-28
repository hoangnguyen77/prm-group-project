package com.example.prm392.ui.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm392.data.District
import com.example.prm392.data.Province
import com.example.prm392.data.Ward
import com.example.prm392.repository.AddressRepository
import kotlinx.coroutines.launch

class AddressViewModel : ViewModel() {

    // States for provinces, districts, and wards
    val provinces = mutableStateOf<List<Province>>(emptyList())
    val districts = mutableStateOf<List<District>>(emptyList())
    val wards = mutableStateOf<List<Ward>>(emptyList())

    // Loading states
    val isLoadingProvinces = mutableStateOf(false)
    val isLoadingDistricts = mutableStateOf(false)
    val isLoadingWards = mutableStateOf(false)

    // Error message state
    val errorMessage = mutableStateOf<String?>(null)

    private val repository = AddressRepository()

    // Fetch provinces from API
    fun fetchProvinces() {
        viewModelScope.launch {
            isLoadingProvinces.value = true
            try {
                provinces.value = repository.fetchProvinces()
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage ?: "Failed to load provinces"
            } finally {
                isLoadingProvinces.value = false
            }
        }
    }

    // Fetch districts given a province ID
    fun fetchDistricts(provinceId: String) {
        viewModelScope.launch {
            isLoadingDistricts.value = true
            try {
                districts.value = repository.fetchDistricts(provinceId)
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage ?: "Failed to load districts"
            } finally {
                isLoadingDistricts.value = false
            }
        }
    }

    // Fetch wards given a district ID
    fun fetchWards(districtId: String) {
        viewModelScope.launch {
            isLoadingWards.value = true
            try {
                wards.value = repository.fetchWards(districtId)
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage ?: "Failed to load wards"
            } finally {
                isLoadingWards.value = false
            }
        }
    }
}