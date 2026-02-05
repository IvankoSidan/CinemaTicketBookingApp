package com.ivan.cinematicketbooking.features_auth.data.model

data class AuthResponseDto(
    val accessToken: String,
    val username: String,
    val email: String
)