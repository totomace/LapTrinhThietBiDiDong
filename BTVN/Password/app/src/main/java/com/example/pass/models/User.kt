package com.uth.smarttasks.models

data class User(
    val email: String,
    val password: String = "",
    val isVerified: Boolean = false
) {
    fun isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(): Boolean {
        return password.length >= 6
    }
}