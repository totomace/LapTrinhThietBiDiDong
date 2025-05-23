package com.example.spike

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.spike.ui.theme.SpikeAppTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Surface

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpikeAppTheme {
                Surface {
                    Text(text = "Welcome to Spike App!")
                }
            }
        }
    }
}
