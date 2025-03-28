package com.example.prm392.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prm392.enum.Screen
import com.example.prm392.ui.view_models.AuthViewModel

@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    onSignInClick: () -> Unit,
    navController: NavController,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "WELCOME ${authViewModel.userModel.value?.fullName ?: "GUEST"}",
                color = Color.White,
                fontSize = 20.sp,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileOption("My Details", Icons.Filled.AccountCircle, {
                navController.navigate(Screen.UserInfo.route)
            })
            ProfileOption("My Orders", Icons.Filled.ShoppingCart, {
                navController.navigate(Screen.Order.route)
            })
            ProfileOption(
                title = if (authViewModel.userModel.value == null) "Sign-In" else "Logout",
                icon = Icons.Filled.ExitToApp,
                onClick = {
                    if (authViewModel.userModel.value == null) {
                        onSignInClick()
                    } else {
                        authViewModel.logout()
                        navController.navigate(Screen.Home.route) // Navigate to home screen
                    }
                }
            )
        }
    }
}

@Composable
fun ProfileOption(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = title, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Navigate", tint = Color.Gray)
        }
    }
}