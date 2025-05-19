package com.example.tun2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputCard(
    hoTen: String,
    onHoTenChange: (String) -> Unit,
    tuoi: String,
    onTuoiChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .width(360.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = androidx.compose.ui.graphics.Color.White
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Họ và tên
            Row {
                Text(
                    text = "Họ và tên",
                    fontSize = 16.sp,
                    modifier = Modifier.width(80.dp)
                )
                OutlinedTextField(
                    value = hoTen,
                    onValueChange = onHoTenChange,
                    singleLine = true,
                    modifier = Modifier
                        .width(250.dp)
                        .height(50.dp)
                )
            }

            // Tuổi
            Row {
                Text(
                    text = "Tuổi",
                    fontSize = 16.sp,
                    modifier = Modifier.width(80.dp)
                )
                OutlinedTextField(
                    value = tuoi,
                    onValueChange = onTuoiChange,
                    singleLine = true,
                    modifier = Modifier
                        .width(250.dp)
                        .height(50.dp)
                )
            }
        }
    }
}
