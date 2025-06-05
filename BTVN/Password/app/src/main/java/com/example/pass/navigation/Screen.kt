package com.uth.smarttasks.navigation

sealed class Screen(val route: String) {
    object ForgotPassword : Screen("forgot_password")
    object Verification : Screen("verification")
    object ResetPassword : Screen("reset_password")
    object Confirm : Screen("confirm")
}