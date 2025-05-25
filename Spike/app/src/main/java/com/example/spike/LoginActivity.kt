package com.example.spike

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.example.spike.ui.screen.LoginScreen
import com.example.spike.ui.screen.RegisterScreen

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                var isLoginScreen by remember { mutableStateOf(true) }

                if (isLoginScreen) {
                    LoginScreen(
                        onLoginClick = { username, password ->
                            // Xử lý đăng nhập
                            println("Username: $username, Password: $password")
                        },
                        onRegisterClick = {
                            // Chuyển sang màn hình đăng ký
                            isLoginScreen = false
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
                } else {
                    RegisterScreen(
                        onRegisterSubmit = { username, email, password, confirmPassword ->
                            // Xử lý đăng ký
                            println("Register info: $username, $email, $password, $confirmPassword")
                            // Ví dụ: kiểm tra mật khẩu khớp, validate email,...
                            // Nếu đăng ký thành công thì quay về màn hình đăng nhập
                            isLoginScreen = true
                        },
                        onBackToLogin = {
                            // Quay về màn hình đăng nhập
                            isLoginScreen = true
                        }
                    )
                }
            }
        }
    }
}
