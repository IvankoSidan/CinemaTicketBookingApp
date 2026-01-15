package com.artur.cinematicketbooking.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artur.cinematicketbooking.domain.model.User
import com.artur.cinematicketbooking.domain.usecase.ClearSessionUseCase
import com.artur.cinematicketbooking.domain.usecase.GetUserProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val clearSessionUseCase: ClearSessionUseCase
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    fun loadProfile() = viewModelScope.launch {
        _user.value = getUserProfileUseCase()
    }

    fun logout() = viewModelScope.launch {
        clearSessionUseCase.invoke()
        _user.value = null
    }
}
