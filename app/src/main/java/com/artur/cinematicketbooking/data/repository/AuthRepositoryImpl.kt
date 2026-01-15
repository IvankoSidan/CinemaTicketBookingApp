package com.artur.cinematicketbooking.data.repository

import com.artur.cinematicketbooking.common.toDomain
import com.artur.cinematicketbooking.data.api.AuthApi
import com.artur.cinematicketbooking.data.model.AuthRequestDto
import com.artur.cinematicketbooking.data.model.AuthResponseDto
import com.artur.cinematicketbooking.domain.model.User
import com.artur.cinematicketbooking.domain.repository.AuthRepository

class AuthRepositoryImpl(private val api: AuthApi) : AuthRepository {

    override suspend fun register(name: String, email: String, password: String): AuthResponseDto {
        val dto = AuthRequestDto(name = name, email = email, password = password)
        return api.register(dto)
    }

    override suspend fun login(email: String, password: String): AuthResponseDto {
        val dto = AuthRequestDto(name = null, email = email, password = password)
        return api.login(dto)
    }
}

