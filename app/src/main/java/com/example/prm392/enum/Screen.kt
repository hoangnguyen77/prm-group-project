package com.example.prm392.enum

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object SignIn : Screen("sign_in")
    object ProductDetails : Screen("productDetail/{productId}")
    object Category : Screen("category/{categoryId}")
}