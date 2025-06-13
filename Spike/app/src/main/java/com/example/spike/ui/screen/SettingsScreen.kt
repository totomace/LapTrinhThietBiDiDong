package com.example.spike.ui.screen

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.spike.LoginActivity
import com.example.spike.preferences.UserPreference

@Composable
fun SettingsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val userPref = remember { UserPreference(context) }

    val currentUser = userPref.getCurrentUser()

    // Log thử để kiểm tra lỗi
    Log.d("SettingsScreen", "CurrentUser: $currentUser")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (currentUser != null) {
            Text("Xin chào, ${currentUser.username}", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Email: ${currentUser.email}", fontSize = 16.sp)
        } else {
            Text("Không có người dùng nào đang đăng nhập", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(onClick = {
            userPref.logout()
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
            (context as? Activity)?.finish()
        }) {
            Text("Đăng xuất")
        }
    }
}
