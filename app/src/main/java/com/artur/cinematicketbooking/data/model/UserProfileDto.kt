package com.artur.cinematicketbooking.data.model

import java.time.LocalDateTime

data class UserProfileDto(
    val id: Long,
    val username: String,
    val email: String,
    val createdAt: LocalDateTime,
    val totalBookings: Long
)