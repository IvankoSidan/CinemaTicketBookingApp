package com.artur.cinematicketbooking.data.api

import com.artur.cinematicketbooking.data.model.AuthRequestDto
import com.artur.cinematicketbooking.data.model.AuthResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/auth/login")
    suspend fun login(@Body request: AuthRequestDto): AuthResponseDto

    @POST("/api/auth/register")
    suspend fun register(@Body request: AuthRequestDto): AuthResponseDto
}