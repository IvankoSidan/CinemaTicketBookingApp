package com.ivan.cinematicketbooking.features_auth.domain.usecase

import com.ivan.cinematicketbooking.features_auth.domain.repository.AuthRepository
import javax.inject.Inject


class RegisterUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(name: String, email: String, password: String) =
        repository.register(name, email, password)
}