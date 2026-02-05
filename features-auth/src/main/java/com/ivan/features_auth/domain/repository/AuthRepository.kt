package com.ivan.cinematicketbooking.features_auth.domain.repository

import com.ivan.cinematicketbooking.features_auth.domain.model.AuthUser

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthUser
    suspend fun register(name: String, email: String, password: String): AuthUser
}