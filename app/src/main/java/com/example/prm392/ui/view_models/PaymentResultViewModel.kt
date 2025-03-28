package com.example.prm392.ui.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PaymentResultViewModel : ViewModel() {
    // When true, the payment is completed and Home should be shown.
    val paymentCompleted = mutableStateOf(false)
}