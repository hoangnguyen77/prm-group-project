package com.example.prm392.ui.screen.cart

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.prm392.MainActivity
import com.example.prm392.PaymentWebViewActivity
import com.example.prm392.data.AddressToCheckoutRequest
import com.example.prm392.data.CartItem
import com.example.prm392.repository.PaymentRepository
import com.example.prm392.ui.view_models.AuthViewModel
import com.example.prm392.ui.view_models.CartViewModel
import com.example.prm392.ui.view_models.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun CartScreen(
    authViewModel: AuthViewModel,
    cartViewModel: CartViewModel,
    profileViewModel: ProfileViewModel
) {
    val paymentRepository = PaymentRepository()
    val context = LocalContext.current // Define context here
    val user = authViewModel.userModel.value
    val cartItems = cartViewModel.cartItems.value
    val isLoading = cartViewModel.isLoading.value
    val errorMessage = cartViewModel.errorMessage.value

    // State to control whether the checkout dialog is shown.
    var showCheckoutDialog by remember { mutableStateOf(false) }

    var paymentUrl by remember { mutableStateOf<String?>(null) } // State for payment URL
    val coroutineScope = rememberCoroutineScope() // Coroutine scope for async tasks


    // Handle navigation when paymentUrl changes
    LaunchedEffect(paymentUrl) {
        paymentUrl?.let { url ->
            val intent = Intent(context, PaymentWebViewActivity::class.java).apply {
                putExtra("paymentUrl", url)
            }
            (context as? Activity)?.startActivityForResult(intent, MainActivity.REQUEST_CODE)
            paymentUrl = null // Reset to prevent repeated navigation
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (user == null) {
            Text(
                text = "Please Login to view your cart",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 32.dp),
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            LaunchedEffect(Unit) {
                cartViewModel.fetchCartItems()
            }

            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(top = 32.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                errorMessage != null -> {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 32.dp)
                    )
                }
                else -> {
                    if (cartItems.isEmpty()) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(top = 64.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Empty Cart",
                                modifier = Modifier.size(120.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "YOUR CART IS EMPTY",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Add some items to get started!",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f)
                        ) {
                            items(cartItems) { item ->
                                CartItemCard(
                                    item = item,
                                    onIncreaseQuantity = {
                                        cartViewModel.updateCartQuantity(item.productId, item.quantity + 1)
                                    },
                                    onDecreaseQuantity = {
                                        if (item.quantity > 1) {
                                            cartViewModel.updateCartQuantity(item.productId, item.quantity - 1)
                                        } else {
                                            cartViewModel.deleteFromCart(item.productId)
                                        }
                                    },
                                    onDelete = {
                                        cartViewModel.deleteFromCart(item.productId)
                                    }
                                )
                            }
                        }

                        val total = cartItems.sumOf { it.price * it.quantity }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "TOTAL: $total VND",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                profileViewModel.fetchUserInformation()
                                showCheckoutDialog = true
                            },
                            enabled = cartItems.isNotEmpty(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = "CHECKOUT",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
    }
    // Modal dialog for checkout showing address information
    if (showCheckoutDialog) {
        val userInfo = profileViewModel.userInformation.value
        val addressText = if (userInfo != null) {
            "${userInfo.address}, ${userInfo.commune}, ${userInfo.district}, ${userInfo.province}"
        } else {
            "Address information not available."
        }
        AlertDialog(
            onDismissRequest = { showCheckoutDialog = false },
            title = {
                Text("Confirm Checkout")
            },
            text = {
                Text(text = addressText)
            },
            confirmButton = {
                Button(
                    onClick =  {
                        val userInfo = profileViewModel.userInformation.value
                        if (userInfo != null) {
                            coroutineScope.launch {
                                try {
                                    val request = AddressToCheckoutRequest(
                                        address = "${userInfo.address}, ${userInfo.commune}, ${userInfo.district}, ${userInfo.province}"
                                    )
                                    paymentUrl = paymentRepository.checkout(request) // Call suspend function


                                } catch (e: Exception) {
                                    // Handle error (e.g., show a toast or log it)
                                    println("Checkout failed: ${e.message}")
                                }
                            }
                        }
                    }
                ) {
                    Text("Continue")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showCheckoutDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun CartItemCard(
    item: CartItem,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            Image(
                painter = rememberAsyncImagePainter(item.mainImage),
                contentDescription = item.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )

            // Product Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${item.price} VND",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Quantity Controls and Delete
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onDecreaseQuantity) {
                        Text(
                            text = "-",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = item.quantity.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    IconButton(onClick = onIncreaseQuantity) {
                        Text(
                            text = "+",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}