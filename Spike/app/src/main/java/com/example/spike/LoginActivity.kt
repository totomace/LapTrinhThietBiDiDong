package com.example.spike

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                onLoginClick = { username, password ->
                    // Xử lý đăng nhập ở đây
                },
                onRegisterClick = {
                    // Chuyển sang RegisterActivity
                    startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                    finish()
                },
                onForgotPasswordClick = {
                    // Xử lý quên mật khẩu
                },
                onFacebookLoginClick = {
                    // Xử lý đăng nhập Facebook
                },
                onGoogleLoginClick = {
                    // Xử lý đăng nhập Google
                },
                onLoginLinkClick = {
                    // Bạn đã ở màn hình đăng nhập rồi, có thể để trống hoặc thông báo
                }
            )
        }
    }
}

@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onFacebookLoginClick: () -> Unit,
    onGoogleLoginClick: () -> Unit,
    onLoginLinkClick: () -> Unit,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Đăng nhập",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Tài khoản") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mật khẩu") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onLoginClick(username, password) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Tiếp tục", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Hoặc đăng nhập bằng",
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.facebook),
                contentDescription = "Đăng nhập Facebook",
                modifier = Modifier
                    .size(85.dp)
                    .clickable { onFacebookLoginClick() }
            )
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Đăng nhập Google",
                modifier = Modifier
                    .size(50.dp)
                    .clickable { onGoogleLoginClick() }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Dòng "Bạn chưa có tài khoản? Đăng ký"
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Bạn chưa có tài khoản? ", color = Color.Gray)
            Text(
                text = "Đăng ký",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onRegisterClick() }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Quên mật khẩu?",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { onForgotPasswordClick() }
        )
    }
}
