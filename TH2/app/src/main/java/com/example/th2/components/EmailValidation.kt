package com.example.th2.components

import androidx.compose.runtime.Composable
import com.example.th2.utils.isValidEmail

@Composable
fun EmailValidation(
    email: String,
    onValidationResult: (String, Boolean) -> Unit
) {
    val message = if (isValidEmail(email)) {
        "Email đúng định dạng"
    } else {
        "Email không đúng định dạng"
    }

    onValidationResult(message, isValidEmail(email))
}
