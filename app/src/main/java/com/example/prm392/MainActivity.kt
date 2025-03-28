package com.example.prm392

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.prm392.data.UpdateOrderRequest
import com.example.prm392.ui.screen.home.HomeScreen
import com.example.prm392.ui.theme.Prm392Theme
import com.example.prm392.ui.view_models.CartViewModel
import com.example.prm392.ui.view_models.PaymentResultViewModel

class MainActivity : ComponentActivity() {
    private val cartViewModel: CartViewModel by viewModels()
    private val paymentResultViewModel: PaymentResultViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Prm392Theme {
                HomeScreen()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val orderCode = data?.getStringExtra("orderCode") ?: ""
            val orderStatus = data?.getStringExtra("orderStatus") ?: ""
            val request = UpdateOrderRequest(
                orderCode = orderCode,
            )
            cartViewModel.updateOrderStatus(request)
            if (orderStatus == "success") {
                // Handle successful payment
                Toast.makeText(this, "Payment successful! Order Code: $orderCode", Toast.LENGTH_LONG).show()
            } else {
                // Handle failed payment
                Toast.makeText(this, "Payment failed! Order Code: $orderCode", Toast.LENGTH_LONG).show()
            }
            paymentResultViewModel.paymentCompleted.value = true
        }
    }

    companion object {
        const val REQUEST_CODE = 1001
    }
}