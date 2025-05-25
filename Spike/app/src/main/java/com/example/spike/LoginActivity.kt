package com.example.spike

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.spike.ui.screen.LoginScreen

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                onLoginClick = { username, password ->
                    // TODO: Xử lý đăng nhập ở đây
                },
                onRegisterClick = {
                    // Chuyển sang màn hình đăng ký
                    startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                    finish()
                },
                onForgotPasswordClick = {
                    // TODO: Xử lý quên mật khẩu
                },
                onFacebookLoginClick = {
                    // TODO: Xử lý đăng nhập Facebook
                },
                onGoogleLoginClick = {
                    // TODO: Xử lý đăng nhập Google
                }
            )
        }
    }
}
