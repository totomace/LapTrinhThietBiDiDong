package com.example.tun2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen() {
    var hoTen by remember { mutableStateOf("") }
    var tuoi by remember { mutableStateOf("") }
    var phanLoai by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "THỰC HÀNH 01",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1)
            )

            InputCard(
                hoTen = hoTen,
                onHoTenChange = { hoTen = it },
                tuoi = tuoi,
                onTuoiChange = { tuoi = it }
            )

            if (phanLoai.isNotEmpty()) {
                Text(
                    text = phanLoai,
                    color = Color(0xFFD32F2F),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            ResultRow(
                onCheck = {
                    val tuoiSo = tuoi.toIntOrNull()
                    phanLoai = if (tuoiSo != null) {
                        when {
                            tuoiSo > 65 -> "Phân loại: Người già"
                            tuoiSo >= 6 -> "Phân loại: Người lớn"
                            tuoiSo >= 2 -> "Phân loại: Trẻ em"
                            else -> "Phân loại: Em bé"
                        }
                    } else {
                        "Tuổi không hợp lệ!"
                    }
                },
                onReset = {
                    hoTen = ""
                    tuoi = ""
                    phanLoai = ""
                }
            )
        }
    }
}
