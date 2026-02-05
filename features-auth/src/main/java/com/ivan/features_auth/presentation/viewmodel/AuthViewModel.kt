package com.ivan.features_auth.presentation.viewmodel

import com.ivan.cinematicketbooking.core.base.BaseViewModel
import com.ivan.cinematicketbooking.core.base.NavigationEvent
import com.ivan.cinematicketbooking.features_auth.domain.model.AuthUser
import com.ivan.cinematicketbooking.features_auth.domain.usecase.LoginUseCase
import com.ivan.cinematicketbooking.features_auth.domain.usecase.RegisterUseCase
import com.ivan.core.util.MutableTokenProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val tokenProvider: MutableTokenProvider
) : BaseViewModel() {

    private val _authState = MutableStateFlow<Result<AuthUser>?>(null)
    val authState: StateFlow<Result<AuthUser>?> = _authState

    fun login(email: String, pass: String) {
        launch {
            val user = loginUseCase(email, pass)
            tokenProvider.updateToken(user.token)
            _authState.value = Result.success(user)
            navigate(NavigationEvent.ToHome)
        }
    }

    fun register(name: String, email: String, pass: String) {
        launch {
            val user = registerUseCase(name, email, pass)
            tokenProvider.updateToken(user.token)
            _authState.value = Result.success(user)
            navigate(NavigationEvent.ToHome)
        }
    }

    fun checkSession() {
        if (tokenProvider.getToken() != null) {
            navigate(NavigationEvent.ToHome)
        }
    }
}