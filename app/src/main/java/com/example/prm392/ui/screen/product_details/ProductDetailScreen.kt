package com.example.prm392.ui.screen.product_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.prm392.ui.view_models.ProductViewModel

@Composable
fun ProductDetailScreen(
    productId: String,
    productViewModel: ProductViewModel,
    onBackClick: () -> Unit = {}
) {
    val product = productViewModel.getProductById(productId)
    var isDescriptionExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp), // Reduced padding
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp) // Reduced padding
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()), // Enable scrolling
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Product Image
                    product?.let {
                        val imageUrl = it.productImages?.firstOrNull { img -> img.isMain == true }?.imageUrl
                            ?: it.productImages?.firstOrNull()?.imageUrl
                            ?: ""
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = it.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp), // Larger image
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Product Name
                    Text(
                        text = product?.name ?: "Unknown Product",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp // Larger font
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Product Description with "Read More"
                    val description = product?.description ?: ""
                    if (description.isNotEmpty()) {
                        val maxLines = if (isDescriptionExpanded) Int.MAX_VALUE else 2
                        val overflow = if (isDescriptionExpanded) TextOverflow.Clip else TextOverflow.Ellipsis
                        val annotatedString = buildAnnotatedString {
                            append(description)
                            if (!isDescriptionExpanded && description.lines().size > 2) {
                                append(" ...")
                                withStyle(style = SpanStyle(color = Color.Blue)) {
                                    append("Read More")
                                }
                            }
                        }
                        ClickableText(
                            text = annotatedString,
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                            maxLines = maxLines,
                            overflow = overflow,
                            onClick = {
                                if (!isDescriptionExpanded) {
                                    isDescriptionExpanded = true
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Quantity and Price
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Quantity",
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${product?.quantity ?: 0}",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp) // Larger font
                            )
                        }
                        Text(
                            text = "${product?.price ?: 0.0} VND",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp // Larger font
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Add to Cart Button
                    Button(
                        onClick = { /* Handle Add to Cart */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "ADD TO CART",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}