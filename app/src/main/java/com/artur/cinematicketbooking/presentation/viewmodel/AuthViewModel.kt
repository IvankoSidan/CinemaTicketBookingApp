package com.artur.cinematicketbooking.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artur.cinematicketbooking.data.model.AuthResponseDto
import com.artur.cinematicketbooking.domain.model.User
import com.artur.cinematicketbooking.domain.usecase.LoginUseCase
import com.artur.cinematicketbooking.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<Result<AuthResponseDto>?>(null)
    val authState: StateFlow<Result<AuthResponseDto>?> = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            runCatching { loginUseCase(email, password) }
                .onSuccess { _authState.value = Result.success(it) }
                .onFailure { _authState.value = Result.failure(it) }
        }
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            runCatching { registerUseCase(name, email, password) }
                .onSuccess { _authState.value = Result.success(it) }
                .onFailure { _authState.value = Result.failure(it) }
        }
    }
}

