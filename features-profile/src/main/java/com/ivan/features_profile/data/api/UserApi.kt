package com.ivan.features_profile.data.api

import com.ivan.features_profile.data.model.UserProfileDto
import retrofit2.http.GET

interface UserApi {
    @GET("/api/users/profile")
    suspend fun getUserProfile(): UserProfileDto
}