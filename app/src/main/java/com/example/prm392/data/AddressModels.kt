package com.example.prm392.data

data class Province(
    val idProvince: String,
    val name: String
)

data class District(
    val idDistrict: String,
    val name: String
)

data class Ward(
    val idCommune: String,
    val name: String
)

data class AddressToCheckoutRequest(
    val address: String,
)

