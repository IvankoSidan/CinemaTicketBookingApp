package com.artur.cinematicketbooking.domain.usecase

import com.artur.cinematicketbooking.data.model.AuthResponseDto
import com.artur.cinematicketbooking.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(name: String, email: String, password: String): AuthResponseDto =
        repository.register(name, email, password)
}
