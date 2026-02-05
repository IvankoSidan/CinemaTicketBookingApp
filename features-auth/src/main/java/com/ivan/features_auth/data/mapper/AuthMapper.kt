package com.ivan.cinematicketbooking.features_auth.data.mapper

import com.ivan.cinematicketbooking.features_auth.data.model.AuthResponseDto
import com.ivan.cinematicketbooking.features_auth.domain.model.AuthUser

fun AuthResponseDto.toDomain() = AuthUser(
    token = accessToken,
    username = username,
    email = email
)