package com.example.spike

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegisterScreen(
                onRegisterClick = { username, email, password, confirmPassword ->
                    // TODO: Xử lý đăng ký
                },
                onLoginClick = {
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                }
            )
        }
    }
}
