package com.ivan.cinematicketbooking.features_auth.domain.usecase

import com.ivan.cinematicketbooking.features_auth.data.model.AuthResponseDto
import com.ivan.cinematicketbooking.features_auth.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) =
        repository.login(email, password)
}