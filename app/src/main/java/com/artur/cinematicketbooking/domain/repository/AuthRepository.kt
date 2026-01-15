package com.artur.cinematicketbooking.domain.repository

import com.artur.cinematicketbooking.data.model.AuthResponseDto
import com.artur.cinematicketbooking.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResponseDto
    suspend fun register(name: String, email: String, password: String): AuthResponseDto
}
