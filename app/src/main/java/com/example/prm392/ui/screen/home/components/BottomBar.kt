package com.example.prm392.ui.screen.home.components

import android.provider.CalendarContract
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.prm392.enum.Screen

import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState



@Composable
fun BottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        Screen.Home,
        Screen.Cart,
        Screen.Profile,
    )

    NavigationBar(
        modifier = Modifier.height(90.dp),
    ) {
        items.forEach { screen ->
            val isSelected = currentRoute == screen.route

            NavigationBarItem(
                label = { Text(screen.route.capitalize()) },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(screen.route) {
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    val icon = when (screen) {
                        Screen.Home -> Icons.Default.Home
                        Screen.Cart -> Icons.Default.ShoppingCart
                        Screen.Profile -> Icons.Default.AccountCircle
                        Screen.SignIn -> Icons.Default.Home
                        Screen.Category -> Icons.Default.Home
                        Screen.ProductDetails -> Icons.Default.Home
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = screen.route,
                        tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )
        }
    }
}




