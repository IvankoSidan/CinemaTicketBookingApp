package com.ivan.features_profile.domain.usecase

import com.ivan.features_profile.domain.model.User
import com.ivan.features_profile.domain.repository.UserRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(): User = repository.getUserProfile()
}