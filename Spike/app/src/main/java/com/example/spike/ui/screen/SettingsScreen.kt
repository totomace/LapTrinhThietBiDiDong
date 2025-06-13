package com.example.spike.ui.screen

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.spike.LoginActivity
import com.example.spike.preferences.UserPreference
import kotlin.random.Random

/**
 * SettingsScreen displays the user profile and settings options.
 * It shows an avatar, user details, and provides actions like logout and future settings.
 */
@Composable
fun SettingsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val userPref = remember { UserPreference(context) }
    val currentUser = userPref.getCurrentUser() // Không dùng mutableStateOf

    // Log for debugging
    Log.d("SettingsScreen", "CurrentUser: $currentUser")

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // User profile section
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    if (currentUser != null) {
                        UserProfileSection(
                            username = currentUser.username,
                            email = currentUser.email
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        SettingsOptionsSection(
                            onEditProfileClick = { /* TODO: Implement edit profile */ },
                            onChangePasswordClick = { /* TODO: Implement change password */ },
                            onNotificationSettingsClick = { /* TODO: Implement notification settings */ },
                            onPrivacySettingsClick = { /* TODO: Implement privacy settings */ }
                        )
                    } else {
                        NoUserSection(
                            onLoginClick = {
                                val intent = Intent(context, LoginActivity::class.java)
                                context.startActivity(intent)
                                (context as? Activity)?.finish()
                            }
                        )
                    }
                }

                // Logout button (only shown when logged in)
                if (currentUser != null) {
                    LogoutButton(
                        onLogoutClick = {
                            userPref.logout()
                            val intent = Intent(context, LoginActivity::class.java)
                            context.startActivity(intent)
                            (context as? Activity)?.finish()
                        }
                    )
                }
            }
        }
    )
}

/**
 * Displays the user profile with an avatar, username, and email.
 */
@Composable
private fun UserProfileSection(username: String, email: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 16.dp)
    ) {
        UserAvatar(username = username)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Xin chào, $username",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = email,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
    }
}

/**
 * Displays the user avatar with the first letter of the username and a random background color.
 */
@Composable
private fun UserAvatar(username: String) {
    // Get the first letter of username (uppercase)
    val initial = username.firstOrNull()?.uppercase() ?: "?"

    // Generate a fixed random color based on username hash
    val hash = username.hashCode()
    val random = Random(hash)
    val randomColor = Color(
        red = random.nextFloat(),
        green = random.nextFloat(),
        blue = random.nextFloat(),
        alpha = 1f
    )

    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(randomColor)
            .clickable { /* TODO: Allow avatar editing in future */ },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 40.sp,
                fontWeight = FontWeight.SemiBold
            ),
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Displays settings options as a list of clickable items.
 */
@Composable
private fun SettingsOptionsSection(
    onEditProfileClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onNotificationSettingsClick: () -> Unit,
    onPrivacySettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SettingsOptionItem(
            title = "Chỉnh sửa hồ sơ",
            onClick = onEditProfileClick
        )
        SettingsOptionItem(
            title = "Thay đổi mật khẩu",
            onClick = onChangePasswordClick
        )
        SettingsOptionItem(
            title = "Cài đặt thông báo",
            onClick = onNotificationSettingsClick
        )
        SettingsOptionItem(
            title = "Cài đặt quyền riêng tư",
            onClick = onPrivacySettingsClick
        )
    }
}

/**
 * Individual settings option item with a clickable surface.
 */
@Composable
private fun SettingsOptionItem(title: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * Displays a message and login button when no user is logged in.
 */
@Composable
private fun NoUserSection(onLoginClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Text(
            text = "Không có người dùng nào đang đăng nhập",
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onLoginClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Đi đến Đăng nhập")
        }
    }
}

/**
 * Logout button at the bottom of the screen.
 */
@Composable
private fun LogoutButton(onLogoutClick: () -> Unit) {
    Button(
        onClick = onLogoutClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = "Đăng xuất",
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp)
        )
    }
}