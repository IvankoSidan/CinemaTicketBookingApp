package com.artur.cinematicketbooking.domain.usecase

import com.artur.cinematicketbooking.domain.model.User
import com.artur.cinematicketbooking.domain.repository.UserRepository

class GetUserProfileUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(): User = repository.getUserProfile()
}