package com.artur.cinematicketbooking.data.model

data class AuthResponseDto(
    val accessToken: String,
    val username: String,
    val email: String
)
