package com.example.prm392

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.prm392.ui.screen.home.HomeScreen
import com.example.prm392.ui.theme.Prm392Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Prm392Theme {
                HomeScreen()
            }
        }
    }
}