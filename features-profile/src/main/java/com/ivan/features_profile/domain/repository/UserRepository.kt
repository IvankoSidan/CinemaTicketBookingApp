package com.ivan.features_profile.domain.repository

import com.ivan.features_profile.domain.model.User


interface UserRepository {
    suspend fun getUserProfile(): User
    suspend fun logout()
}