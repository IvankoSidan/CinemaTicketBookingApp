package com.artur.cinematicketbooking.domain.repository

import com.artur.cinematicketbooking.domain.model.User

interface UserRepository {
    suspend fun getUserProfile(): User
}