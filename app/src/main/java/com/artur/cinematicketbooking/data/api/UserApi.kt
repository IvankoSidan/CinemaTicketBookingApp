package com.artur.cinematicketbooking.data.api

import com.artur.cinematicketbooking.data.model.UserProfileDto
import retrofit2.http.GET

interface UserApi {
    @GET("/api/users/profile")
    suspend fun getUserProfile(): UserProfileDto
}