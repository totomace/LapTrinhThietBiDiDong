package com.example.spike

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.spike.ui.screen.LoginScreen

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                LoginScreen(
                    onLoginClick = { username, password ->
                        // Xử lý đăng nhập
                        println("Username: $username, Password: $password")
                    },
                    onRegisterClick = {
                        // Xử lý đăng ký
                    },
                    onForgotPasswordClick = {
                        // Xử lý quên mật khẩu
                    },
                    onFacebookLoginClick = {
                        // Xử lý đăng nhập Facebook
                    },
                    onGoogleLoginClick = {
                        // Xử lý đăng nhập Google
                    }
                )
            }
        }
    }
}