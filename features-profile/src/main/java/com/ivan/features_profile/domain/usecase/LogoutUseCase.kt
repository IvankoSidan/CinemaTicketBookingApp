package com.ivan.features_profile.domain.usecase

import com.ivan.features_profile.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke() = repository.logout()
}