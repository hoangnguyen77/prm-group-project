package com.example.prm392.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.prm392.enum.Screen
import com.example.prm392.ui.screen.cart.CartScreen
import com.example.prm392.ui.screen.category.CategoryScreen
import com.example.prm392.ui.screen.home.components.BottomBar
import com.example.prm392.ui.screen.home.components.HomeContent
import com.example.prm392.ui.screen.order.OrderScreen
import com.example.prm392.ui.screen.product_details.ProductDetailScreen
import com.example.prm392.ui.screen.profile.ProfileScreen
import com.example.prm392.ui.screen.profile.UserInfoScreen
import com.example.prm392.ui.screen.sign_in.SignInScreen
import com.example.prm392.ui.view_models.AddressViewModel
import com.example.prm392.ui.view_models.AuthViewModel
import com.example.prm392.ui.view_models.CartViewModel
import com.example.prm392.ui.view_models.CategoryViewModel
import com.example.prm392.ui.view_models.OrderViewModel
import com.example.prm392.ui.view_models.PaymentResultViewModel
import com.example.prm392.ui.view_models.ProductViewModel
import com.example.prm392.ui.view_models.ProfileViewModel

@Composable
fun HomeScreen(
    productViewModel: ProductViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel (),
    cartViewModel: CartViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel(),
    orderViewModel: OrderViewModel = viewModel(),
    addressViewModel: AddressViewModel = viewModel(),
    paymentResultViewModel: PaymentResultViewModel = viewModel(),
) {
    val navController = rememberNavController()
    LaunchedEffect(paymentResultViewModel.paymentCompleted.value) {
        if (paymentResultViewModel.paymentCompleted.value) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Home.route) { inclusive = true }
            }
            paymentResultViewModel.paymentCompleted.value = false // Reset flag
        }
    }
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavigationGraph(
                navController = navController,
                categoryViewModel = categoryViewModel,
                productViewModel = productViewModel,
                authViewModel = authViewModel,
                cartViewModel = cartViewModel,
                profileViewModel = profileViewModel,
                orderViewModel = orderViewModel,
                addressViewModel = addressViewModel
            )
        }
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    categoryViewModel: CategoryViewModel,
    productViewModel: ProductViewModel,
    authViewModel: AuthViewModel,
    cartViewModel: CartViewModel,
    profileViewModel : ProfileViewModel,
    orderViewModel: OrderViewModel,
    addressViewModel: AddressViewModel
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeContent(
                categoryViewModel = categoryViewModel,
                productViewModel = productViewModel,
                navController = navController // Pass NavController to HomeContent
            )
        }
        composable(Screen.Cart.route) { CartScreen(
            authViewModel = authViewModel,
            cartViewModel = cartViewModel,
            profileViewModel = profileViewModel
        ) }
        composable(Screen.Profile.route) {
            ProfileScreen(
                authViewModel = authViewModel,
                onSignInClick = { navController.navigate("sign_in") },
                navController = navController
            )
        }
        composable(Screen.SignIn.route) {
            SignInScreen(
                authViewModel = authViewModel,
                onSignUpClick = { /* TODO: Navigate to Sign-Up */ },
                onForgotPasswordClick = { /* TODO: Navigate to Forgot Password */ },
                onSignInSuccess = { navController.navigate(Screen.Home.route) }
            )
        }
        composable(
            route = Screen.ProductDetails.route,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(
                productId = productId,
                productViewModel = productViewModel,
                onBackClick = { navController.popBackStack() },
                cartViewModel = cartViewModel,
                authViewModel = authViewModel,
            )
        }

        composable(
            route = Screen.Category.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
            CategoryScreen(
                categoryId = categoryId,
                productViewModel = productViewModel,
                categoryViewModel = categoryViewModel,
                navController = navController
            )
        }

        composable (Screen.UserInfo.route)
        {
            UserInfoScreen(
                authViewModel = authViewModel,
                profileViewModel = profileViewModel,
                addressViewModel = addressViewModel
            )
        }

        composable(Screen.Order.route) {
            OrderScreen(
                orderViewModel = orderViewModel,
                authViewModel = authViewModel,
            )
        }
    }
}