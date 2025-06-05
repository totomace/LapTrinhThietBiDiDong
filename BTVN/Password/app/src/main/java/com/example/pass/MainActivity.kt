package com.uth.smarttasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uth.smarttasks.navigation.Screen
import com.uth.smarttasks.screens.*
import com.uth.smarttasks.ui.theme.SmartTasksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartTasksTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.ForgotPassword.route
                    ) {
                        composable(Screen.ForgotPassword.route) {
                            ForgotPasswordScreen(
                                onNavigateToVerification = { email ->
                                    navController.navigate("${Screen.Verification.route}/$email")
                                }
                            )
                        }

                        composable("${Screen.Verification.route}/{email}") { backStackEntry ->
                            val email = backStackEntry.arguments?.getString("email") ?: ""
                            VerificationScreen(
                                email = email,
                                onNavigateToResetPassword = {
                                    navController.navigate(Screen.ResetPassword.route)
                                },
                                onBack = { navController.popBackStack() }
                            )
                        }

                        composable(Screen.ResetPassword.route) {
                            ResetPasswordScreen(
                                onNavigateToConfirm = { email, password ->
                                    navController.navigate("${Screen.Confirm.route}/$email/$password")
                                },
                                onBack = { navController.popBackStack() }
                            )
                        }

                        composable("${Screen.Confirm.route}/{email}/{password}") { backStackEntry ->
                            val email = backStackEntry.arguments?.getString("email") ?: ""
                            val password = backStackEntry.arguments?.getString("password") ?: ""
                            ConfirmScreen(
                                email = email,
                                password = password,
                                onComplete = {
                                    // Navigate back to start or main screen
                                    navController.popBackStack(Screen.ForgotPassword.route, false)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}