
package com.ivan.features_profile.presentation.viewmodel

import com.ivan.cinematicketbooking.core.base.BaseViewModel
import com.ivan.cinematicketbooking.core.base.NavigationEvent
import com.ivan.features_profile.domain.model.User
import com.ivan.features_profile.domain.usecase.GetUserProfileUseCase
import com.ivan.features_profile.domain.usecase.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    fun loadProfile() {
        launch {
            _user.value = getUserProfileUseCase()
        }
    }

    fun logout() {
        launch {
            logoutUseCase()
            _user.value = null
            navigate(NavigationEvent.ToAuth)
        }
    }
}