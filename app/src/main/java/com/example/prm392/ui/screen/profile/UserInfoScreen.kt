package com.example.prm392.ui.screen.profile

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.prm392.data.District
import com.example.prm392.data.Province
import com.example.prm392.data.UpdateUserInformationRequest
import com.example.prm392.data.Ward
import com.example.prm392.ui.view_models.AddressViewModel
import com.example.prm392.ui.view_models.AuthViewModel
import com.example.prm392.ui.view_models.ProfileViewModel

@Composable
fun UserInfoScreen(
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel,
    addressViewModel: AddressViewModel
) {
    val context = LocalContext.current // Define context here
    if (authViewModel.userModel.value == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Please login",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        }
        return
    }

    // State variables for user input and selections
    var fullName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var selectedProvince by remember { mutableStateOf<Province?>(null) }
    var selectedDistrict by remember { mutableStateOf<District?>(null) }
    var selectedWard by remember { mutableStateOf<Ward?>(null) }

    val updateStatus by profileViewModel.updateStatus.collectAsState()

    LaunchedEffect(updateStatus) {
        if (updateStatus == true) {
            Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show()
            profileViewModel.resetUpdateStatus() // Reset to prevent repeated toasts
        } else if (updateStatus == false) {
            Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show()
            profileViewModel.resetUpdateStatus()
        }
    }

    // Fetch initial data when the screen loads
    LaunchedEffect(Unit) {
        profileViewModel.fetchUserInformation()
        addressViewModel.fetchProvinces()
    }

    // Set province and trigger district fetch when user info and provinces are available
    LaunchedEffect(profileViewModel.userInformation.value, addressViewModel.provinces.value) {
        val userInfo = profileViewModel.userInformation.value ?: return@LaunchedEffect
        val provinces = addressViewModel.provinces.value
        if (provinces.isNotEmpty() && selectedProvince == null) {
            fullName = userInfo.fullName
            phoneNumber = userInfo.phoneNumber
            address = userInfo.address
            selectedProvince = provinces.find { it.idProvince == userInfo.provinceCode }
            selectedProvince?.let { addressViewModel.fetchDistricts(it.idProvince) }
        }
    }

    // Set district and trigger ward fetch when districts are available
    LaunchedEffect(addressViewModel.districts.value) {
        val userInfo = profileViewModel.userInformation.value ?: return@LaunchedEffect
        val districts = addressViewModel.districts.value
        if (districts.isNotEmpty() && selectedDistrict == null) {
            selectedDistrict = districts.find { it.idDistrict == userInfo.districtCode }
            selectedDistrict?.let { addressViewModel.fetchWards(it.idDistrict) }
        }
    }

    // Set ward when wards are available
    LaunchedEffect(addressViewModel.wards.value) {
        val userInfo = profileViewModel.userInformation.value ?: return@LaunchedEffect
        val wards = addressViewModel.wards.value
        if (wards.isNotEmpty() && selectedWard == null) {
            selectedWard = wards.find { it.idCommune == userInfo.communeCode }
        }
    }

    // Fetch districts when province changes (user interaction)
    LaunchedEffect(selectedProvince) {
        if (selectedProvince != null && addressViewModel.districts.value.isEmpty()) {
            addressViewModel.fetchDistricts(selectedProvince!!.idProvince)
            selectedDistrict = null
            selectedWard = null
        }
    }

    // Fetch wards when district changes (user interaction)
    LaunchedEffect(selectedDistrict) {
        if (selectedDistrict != null && addressViewModel.wards.value.isEmpty()) {
            addressViewModel.fetchWards(selectedDistrict!!.idDistrict)
            selectedWard = null
        }
    }

    // UI Layout
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )
        ProvinceDropdown(
            provinces = addressViewModel.provinces.value,
            selectedProvince = selectedProvince,
            onProvinceSelected = { selectedProvince = it }
        )
        DistrictDropdown(
            districts = addressViewModel.districts.value,
            selectedDistrict = selectedDistrict,
            onDistrictSelected = { selectedDistrict = it }
        )
        WardDropdown(
            wards = addressViewModel.wards.value,
            selectedWard = selectedWard,
            onWardSelected = { selectedWard = it }
        )
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                profileViewModel.updateUserInformation(
                    UpdateUserInformationRequest(
                        phoneNumber = phoneNumber,
                        fullName = fullName,
                        commune = selectedWard?.name ?: "",
                        district = selectedDistrict?.name ?: "",
                        province = selectedProvince?.name ?: "",
                        address = address,
                        provinceCode = selectedProvince?.idProvince ?: "",
                        districtCode = selectedDistrict?.idDistrict ?: "",
                        communeCode = selectedWard?.idCommune ?: ""
                    )
                )
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("APPLY", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}


@Composable
fun ProvinceDropdown(
    provinces: List<Province>,
    selectedProvince: Province?,
    onProvinceSelected: (Province) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedProvince?.name ?: "Select Province",
            onValueChange = {},
            label = { Text("PROVINCE:", fontWeight = FontWeight.Bold) },
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                }
            },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            provinces.forEach { province ->
                DropdownMenuItem(
                    text = {
                        Text(province.name)
                    },
                    onClick = {
                        onProvinceSelected(province)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DistrictDropdown(
    districts: List<District>,
    selectedDistrict: District?,
    onDistrictSelected: (District) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedDistrict?.name ?: "Select District",
            onValueChange = {},
            label = { Text("DISTRICT:", fontWeight = FontWeight.Bold) },
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                }
            },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            districts.forEach { district ->
                DropdownMenuItem(
                    text = {
                        Text(district.name)
                    },
                    onClick = {
                        onDistrictSelected(district)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun WardDropdown(
    wards: List<Ward>,
    selectedWard: Ward?,
    onWardSelected: (Ward) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedWard?.name ?: "Select Ward",
            onValueChange = {},
            label = { Text("WARD:", fontWeight = FontWeight.Bold) },
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                }
            },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            wards.forEach { ward ->
                DropdownMenuItem(
                    text = { Text(ward.name) },
                    onClick = {
                        onWardSelected(ward)
                        expanded = false
                    }
                )
            }
        }
    }
}