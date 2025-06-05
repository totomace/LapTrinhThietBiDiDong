package com.example.firebase

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.firebase.ui.theme.FirebaseTheme
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.foundation.shape.CircleShape
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import androidx.compose.ui.platform.LocalContext

class ProfileActivity : ComponentActivity() {
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val name = intent.getStringExtra("name") ?: "Không có tên"
        val email = intent.getStringExtra("email") ?: "Không có email"
        val phone = intent.getStringExtra("phone") ?: "Không có số điện thoại"
        val photoUrl = intent.getStringExtra("photoUrl") ?: ""

        setContent {
            FirebaseTheme {
                MaterialTheme {
                    ProfileScreen(name, email, phone, photoUrl) {
                        // Xử lý đăng xuất
                        auth.signOut()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(
    name: String,
    email: String,
    phone: String,
    photoUrl: String,
    onSignOut: () -> Unit
) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("787695191417-8vvhvgb6tiugm1j9pfmr6d6d669rpv7c.apps.googleusercontent.com")
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (photoUrl.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(photoUrl),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(text = "Tên: $name", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Email: $email", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Số điện thoại: $phone", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            googleSignInClient.revokeAccess().addOnCompleteListener {
                auth.signOut()
                googleSignInClient.signOut().addOnCompleteListener {
                    onSignOut()
                }
            }
        }) {
            Text(text = "Đăng xuất")
        }
    }
}
