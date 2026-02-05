package com.ivan.cinematicketbooking.features_auth.domain.model

data class AuthUser(
    val token: String,
    val username: String,
    val email: String
)