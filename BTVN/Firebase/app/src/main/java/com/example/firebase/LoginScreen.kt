package com.example.firebase

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun LoginGoogleScreen() {
    val context = LocalContext.current
    val activity = context as Activity

    val auth = FirebaseAuth.getInstance()

    var isLoading by remember { mutableStateOf(false) }

    // Cấu hình Google Sign In
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("787695191417-8vvhvgb6tiugm1j9pfmr6d6d669rpv7c.apps.googleusercontent.com")
        .requestEmail()
        .build()

    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        isLoading = false
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account: GoogleSignInAccount? = task.getResult(Exception::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account.idToken!!, auth, activity) // truyền activity vào đây
                }
            } catch (e: Exception) {
                Log.w("LoginGoogle", "Google sign in failed", e)
                Toast.makeText(context, "Đăng nhập Google thất bại: ${e.message}", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, "Đăng nhập Google bị hủy", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Đăng nhập bằng Google", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = "Google Sign-In",
            modifier = Modifier
                .size(64.dp)
                .clickable {
                    isLoading = true
                    val signInIntent: Intent = googleSignInClient.signInIntent
                    launcher.launch(signInIntent)
                }
        )

        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}

// Sửa hàm firebaseAuthWithGoogle truyền thêm activity để startActivity
private fun firebaseAuthWithGoogle(idToken: String, auth: FirebaseAuth, activity: Activity) {
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    auth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val name = user?.displayName ?: "Không có tên"
                val email = user?.email ?: "Không có email"
                val phone = user?.phoneNumber ?: "Không có số điện thoại"
                val photoUrl = user?.photoUrl.toString()

                val intent = Intent(activity, ProfileActivity::class.java).apply {
                    putExtra("name", name)
                    putExtra("email", email)
                    putExtra("phone", phone)
                    putExtra("photoUrl", photoUrl)
                }
                activity.startActivity(intent)
                activity.finish()
            } else {
                task.exception?.printStackTrace()
                Toast.makeText(activity, "Đăng nhập Firebase thất bại: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
}
