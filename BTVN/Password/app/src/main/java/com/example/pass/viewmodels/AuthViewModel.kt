package com.uth.smarttasks.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uth.smarttasks.models.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    private var currentUser: User? = null

    fun sendVerificationCode(email: String, onSuccess: () -> Unit) {
        if (!User(email).isEmailValid()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Email không hợp lệ")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            delay(1000) // Simulate API call
            currentUser = User(email)
            _uiState.value = _uiState.value.copy(isLoading = false)
            onSuccess()
        }
    }

    fun verifyCode(code: String, onSuccess: () -> Unit) {
        if (code.length != 6) {
            _uiState.value = _uiState.value.copy(errorMessage = "Mã xác minh phải có 6 số")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            delay(1000) // Simulate API call
            currentUser = currentUser?.copy(isVerified = true)
            _uiState.value = _uiState.value.copy(isLoading = false)
            onSuccess()
        }
    }

    fun resetPassword(password: String, confirmPassword: String, onSuccess: () -> Unit) {
        if (password != confirmPassword) {
            _uiState.value = _uiState.value.copy(errorMessage = "Mật khẩu không khớp")
            return
        }

        if (!User("", password).isPasswordValid()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Mật khẩu phải có ít nhất 6 ký tự")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            delay(1000) // Simulate API call
            currentUser = currentUser?.copy(password = password)
            _uiState.value = _uiState.value.copy(isLoading = false)
            onSuccess()
        }
    }

    fun getCurrentUser(): User? = currentUser

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)