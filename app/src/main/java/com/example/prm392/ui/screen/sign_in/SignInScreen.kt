package com.example.prm392.ui.screen.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prm392.ui.view_models.AuthViewModel

@Composable
fun SignInScreen(
    authViewModel: AuthViewModel,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignInSuccess: () -> Unit
) {
    // State for username and password input fields
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    // Observe states from AuthViewModel
    val userModel by authViewModel.userModel
    val isLoading by authViewModel.isLoading
    val errorMessage by authViewModel.errorMessage

    // Navigate to home screen on successful login
    LaunchedEffect(userModel) {
        if (userModel != null) {
            onSignInSuccess()
        }
    }

    // Get keyboard controller
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Title
        Text(
            text = "WELCOME BACK",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Sign in with your email and password\nor sign in with social media",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Username Field
        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("USERNAME") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "User Icon") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field with hidden text
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("PASSWORD") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Lock Icon") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            visualTransformation = PasswordVisualTransformation() // Hide password text
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Forgot Password
        Text(
            text = "FORGOT PASSWORD",
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.Start)
                .clickable { onForgotPasswordClick() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Sign-Up Option
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "DON'T HAVE AN ACCOUNT?", fontSize = 14.sp, color = Color.Black)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "SIGN-UP",
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .clickable { onSignUpClick() }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sign-In Button with keyboard close
        Button(
            onClick = {
                keyboardController?.hide() // Close the keyboard
                authViewModel.login(username.value, password.value)
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = !isLoading // Disable button while loading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(text = "SIGN IN", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Display error message if any
        errorMessage?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = it,
                color = Color.Red,
                fontSize = 14.sp
            )
        }
    }
}