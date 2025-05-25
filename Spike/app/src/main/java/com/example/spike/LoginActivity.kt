package com.example.spike

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.spike.preferences.UserPreference
import com.example.spike.ui.screen.LoginScreen

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userPrefs = UserPreference(this)

        setContent {
            LoginScreen(
                onLoginClick = { username, password ->
                    val user = userPrefs.getUser(username)

                    if (user == null || user.password != password) {
                        Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()

                        // Chuyển sang màn hình chính
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                },
                onRegisterClick = {
                    startActivity(Intent(this, RegisterActivity::class.java))
                    finish()
                },
                onForgotPasswordClick = {},
                onFacebookLoginClick = {},
                onGoogleLoginClick = {}
            )
        }
    }
}
