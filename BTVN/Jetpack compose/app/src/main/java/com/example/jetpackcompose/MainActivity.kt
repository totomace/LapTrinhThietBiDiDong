package com.example.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.jetpackcompose.R
import androidx.compose.ui.text.withStyle
import androidx.compose.material3.ExperimentalMaterial3Api

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeApp()
        }
    }
}

@Composable
fun JetpackComposeApp() {
    var currentScreen by remember { mutableStateOf("splash") }

    when (currentScreen) {
        "splash" -> SplashScreen(onReadyClick = { currentScreen = "list" })
        "list" -> ComponentsList(
            onNavigate = { currentScreen = it },
            onBack = { /* Không có back vì là màn hình chính sau splash */ }
        )
        "text" -> TextDetailScreen(onBack = { currentScreen = "list" })
        "image" -> ImageScreen(onBack = { currentScreen = "list" })
        "textfield" -> TextFieldScreen(onBack = { currentScreen = "list" })
        "rowlayout" -> RowLayoutScreen(onBack = { currentScreen = "list" })
    }
}

@Composable
fun SplashScreen(onReadyClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Nguyễn Văn A", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("2342312323")
        Spacer(modifier = Modifier.height(16.dp))
        Image(painter = painterResource(R.drawable.compose), contentDescription = null)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Jetpack Compose", fontWeight = FontWeight.Bold)
        Text("Jetpack Compose is a modern UI toolkit for building native Android applications using a declarative programming approach.")
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onReadyClick) {
            Text("I'm ready")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentsList(onNavigate: (String) -> Unit, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("UI Components List") }
                // Màn hình list là màn hình chính, nên không cần nút back
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            ComponentButton("Text") { onNavigate("text") }
            ComponentButton("Image") { onNavigate("image") }
            ComponentButton("TextField") { onNavigate("textfield") }
            ComponentButton("PasswordField") { /* Chưa làm */ }
            ComponentButton("Column") { /* Chưa làm */ }
            ComponentButton("Row") { onNavigate("rowlayout") }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {}) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Tự tìm hiểu", color = Color.White)
                    Text("Tìm ra tất cả các thành phần UI cơ bản", color = Color.White, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun ComponentButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(label)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextDetailScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Text Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(16.dp).padding(paddingValues)) {
            Text(
                buildAnnotatedString {
                    append("The ")
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                        append("quick ")
                    }
                    withStyle(style = SpanStyle(color = Color(0xFFFF9800))) {
                        append("Brown")
                    }
                    append("\nfox ")
                    withStyle(style = SpanStyle(letterSpacing = 4.sp)) {
                        append("jumps")
                    }
                    append(" ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("over")
                    }
                    append("\nthe ")
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                        append("lazy")
                    }
                    append(" dog.")
                },
                fontSize = 18.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Images") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(paddingValues)
        ) {

            // Ảnh trong app
            Image(
                painter = painterResource(R.drawable.gtvt),
                contentDescription = "In app",
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "https://giaothongvantai.hcm.edu.vn/wp-content/uploads/2025/01/Logo-GTVT.png",
                color = Color.Blue,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldScreen(onBack: () -> Unit) {
    var input by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TextField") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(16.dp).padding(paddingValues)) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Thông tin nhập") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Tự động cập nhật dữ liệu theo textfield", color = Color.Red)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowLayoutScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Row Layout") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(16.dp).padding(paddingValues)) {
            repeat(4) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(Color(0xFF90CAF9), RoundedCornerShape(8.dp))
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
