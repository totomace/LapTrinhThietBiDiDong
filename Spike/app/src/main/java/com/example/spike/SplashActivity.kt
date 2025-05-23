package com.example.spike

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.spike.ui.components.StrokedText

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SplashScreen {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    val fullText = "Yoursoundtrack, your story"
    var sloganText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        // Hiệu ứng typing từng ký tự
        for (i in fullText.indices) {
            sloganText = fullText.substring(0, i+1)
            delay(20)
        }

        // Đợi 2 giây sau khi typing xong rồi chuyển màn
        delay(2000)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            StrokedText(
                text = sloganText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textColor = Color.LightGray,
                strokeColor = Color.Black,
                strokeWidth = 1.5.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
