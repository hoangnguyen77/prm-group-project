package com.example.prm392.ui.screen.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.prm392.data.Order
import com.example.prm392.ui.view_models.AuthViewModel
import com.example.prm392.ui.view_models.OrderViewModel
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    authViewModel: AuthViewModel,
    orderViewModel: OrderViewModel
) {
    // If user is not logged in, show login prompt
    if (authViewModel.userModel.value == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Please login",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        }
        return
    }

    // Fetch orders when screen is first loaded
    LaunchedEffect(Unit) {
        orderViewModel.fetchOrders()
    }

    // Main order screen content
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Orders",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Show loading indicator or error or order list
            when {
                orderViewModel.isLoading.value -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                orderViewModel.errorMessage.value != null -> {
                    ErrorContent(orderViewModel.errorMessage.value!!)
                }
                else -> {
                    OrderList(orderViewModel.orders.value)
                }
            }
        }
    }
}

@Composable
fun ErrorContent(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = "Error",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Error Loading Orders",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun OrderList(orders: List<Order>) {
    // If no orders, show empty state
    if (orders.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No orders found",
                style = MaterialTheme.typography.titleLarge
            )
        }
        return
    }

    // Lazy column to display orders
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(orders) { order ->
            OrderListItem(order)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun OrderListItem(order: Order) {
    // Format currency
    val vndFormatter = NumberFormat.getInstance(Locale("vi", "VN")).apply {
        maximumFractionDigits = 0 // VND doesn't use decimal places
        currency = Currency.getInstance("VND")
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Order Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Order #${order.code}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                // Status Chip
                OrderStatusChip(order.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Order Details
            Text(
                text = "Delivery Address: ${order.address}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Total: ${vndFormatter.format(order.total)} ₫",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun OrderStatusChip(status: Int) {
    val (chipColor, statusText) = when (status) {
        0 -> Color.Yellow to "Pending"
        1 -> Color.Blue to "Preparing"
        2 -> Color.Cyan to "Shipping"
        3 -> Color.Green to "Completed"
        4 -> Color.Red to "Cancelled"
        5 -> Color.Gray to "Refused"
        else -> Color.Gray to "Unknown"
    }

    Box(
        modifier = Modifier
            .background(chipColor.copy(alpha = 0.2f), shape = MaterialTheme.shapes.small)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = statusText,
            color = chipColor,
            style = MaterialTheme.typography.labelSmall
        )
    }
}