package com.ivan.cinematicketbooking.features_auth.data.model

data class AuthRequestDto(
    val name: String?,
    val email: String,
    val password: String
)
