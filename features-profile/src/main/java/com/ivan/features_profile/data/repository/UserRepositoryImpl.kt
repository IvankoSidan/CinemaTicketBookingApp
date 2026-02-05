package com.ivan.features_profile.data.repository

import com.ivan.core.util.MutableTokenProvider
import com.ivan.features_profile.data.api.UserApi
import com.ivan.features_profile.data.mapper.toDomain
import com.ivan.features_profile.domain.model.User
import com.ivan.features_profile.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: UserApi,
    private val tokenProvider: MutableTokenProvider
) : UserRepository {
    override suspend fun getUserProfile(): User = api.getUserProfile().toDomain()

    override suspend fun logout() {
        tokenProvider.updateToken(null)
    }
}