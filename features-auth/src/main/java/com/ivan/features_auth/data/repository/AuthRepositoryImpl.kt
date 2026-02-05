package com.ivan.cinematicketbooking.features_auth.data.repository

import com.ivan.cinematicketbooking.features_auth.data.api.AuthApi
import com.ivan.cinematicketbooking.features_auth.data.mapper.toDomain
import com.ivan.cinematicketbooking.features_auth.data.model.AuthRequestDto
import com.ivan.cinematicketbooking.features_auth.domain.repository.AuthRepository
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
) : AuthRepository {
    override suspend fun login(email: String, password: String) =
        api.login(AuthRequestDto(null, email, password)).toDomain()

    override suspend fun register(name: String, email: String, password: String) =
        api.register(AuthRequestDto(name, email, password)).toDomain()
}