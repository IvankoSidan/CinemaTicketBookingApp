package com.artur.cinematicketbooking.domain.usecase

import com.artur.cinematicketbooking.data.model.AuthResponseDto
import com.artur.cinematicketbooking.domain.model.User
import com.artur.cinematicketbooking.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): AuthResponseDto =
        repository.login(email, password)
}
