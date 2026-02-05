package com.ivan.cinematicketbooking.features_auth.data.api

import com.ivan.cinematicketbooking.features_auth.data.model.AuthRequestDto
import com.ivan.cinematicketbooking.features_auth.data.model.AuthResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: AuthRequestDto): AuthResponseDto

    @POST("auth/register")
    suspend fun register(@Body request: AuthRequestDto): AuthResponseDto
}