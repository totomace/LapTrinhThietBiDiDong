package com.example.futureteller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                FutureTellerApp()
            }
        }
    }
}

@Composable
fun FutureTellerApp() {
    var prediction by remember { mutableStateOf("Nhấn để xem tương lai của bạn!") }
    var displayedText by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Danh sách dự đoán
    val predictions = listOf(
        "Bạn sẽ gặp một bất ngờ lớn vào ngày mai!",
        "Ai đó đang nghĩ về bạn...",
        "Tình yêu đang đến gần hơn bao giờ hết.",
        "Hãy tránh xa trà sữa hôm nay nhé!",
        "Bạn sẽ đạt điểm cao trong kỳ kiểm tra tới!",
        "Một cơ hội vàng sắp đến, hãy nắm bắt!",
        "Cẩn thận kẻo mất đồ trong tuần này!",
        "Hôm nay là ngày tuyệt vời để học Android."
    )

    // Hiệu ứng nền sóng
    val infiniteTransition = rememberInfiniteTransition()
    val wavePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFE6F0FA),
            Color(0xFFB3CDE0),
            Color(0xFF6497B1)
        ),
        start = Offset(0f, 0f),
        end = Offset(1000f * wavePhase, 1000f * wavePhase)
    )

    // Hiệu ứng rung và phát sáng cho Card
    var shakeTrigger by remember { mutableStateOf(0) }
    val shakeOffset by animateFloatAsState(
        targetValue = if (shakeTrigger > 0) 5f else 0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy),
        finishedListener = { shakeTrigger = 0 }
    )
    val glowAlpha by animateFloatAsState(
        targetValue = if (shakeTrigger > 0) 0.8f else 0f,
        animationSpec = tween(500)
    )

    // Hiệu ứng scale và lấp lánh cho nút
    var buttonScale by remember { mutableStateOf(1f) }
    val buttonAlpha by animateFloatAsState(
        targetValue = if (buttonScale == 1f) 1f else 0.7f,
        animationSpec = tween(100)
    )
    val buttonColor by animateColorAsState(
        targetValue = if (buttonScale == 1f) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
        animationSpec = tween(200)
    )

    // Hiệu ứng đánh máy
    LaunchedEffect(prediction) {
        displayedText = ""
        prediction.forEachIndexed { index, char ->
            displayedText += char
            delay(50) // Tốc độ gõ
        }
        shakeTrigger++ // Kích hoạt hiệu ứng rung và phát sáng
    }

    // Giao diện chính
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Card chứa dự đoán với hiệu ứng phát sáng
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .offset(x = shakeOffset.dp)
                    .border(
                        width = 2.dp,
                        color = Color.White.copy(alpha = glowAlpha),
                        shape = MaterialTheme.shapes.medium
                    ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                AnimatedContent(
                    targetState = displayedText,
                    transitionSpec = {
                        (scaleIn(initialScale = 0.8f) + fadeIn()) togetherWith
                                (scaleOut(targetScale = 0.8f) + fadeOut())
                    },
                    modifier = Modifier.padding(16.dp)
                ) { targetText ->
                    Text(
                        text = targetText,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            shadow = Shadow(
                                color = Color.White.copy(alpha = 0.5f),
                                offset = Offset(4f, 4f),
                                blurRadius = 8f
                            )
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Nút xem dự đoán với hiệu ứng lấp lánh
            Button(
                onClick = {
                    buttonScale = 1.1f
                    scope.launch {
                        delay(100)
                        buttonScale = 1f
                        prediction = predictions[Random.nextInt(predictions.size)]
                        snackbarHostState.showSnackbar("Dự đoán mới đã được tạo!")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(48.dp)
                    .scale(buttonScale)
                    .alpha(buttonAlpha)
                    .animateContentSize(),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text(
                    text = "Xem Dự Đoán",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nút reset
            TextButton(
                onClick = {
                    buttonScale = 1.1f
                    scope.launch {
                        delay(100)
                        buttonScale = 1f
                        prediction = "Nhấn để xem tương lai của bạn!"
                        snackbarHostState.showSnackbar("Đã đặt lại dự đoán!")
                    }
                },
                modifier = Modifier
                    .scale(buttonScale)
                    .alpha(buttonAlpha)
            ) {
                Text(
                    text = "Đặt Lại",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FutureTellerAppPreview() {
    MaterialTheme {
        FutureTellerApp()
    }
}