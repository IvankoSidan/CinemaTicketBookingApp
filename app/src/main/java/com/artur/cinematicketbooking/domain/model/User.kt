package com.artur.cinematicketbooking.domain.model

import java.time.LocalDateTime

data class User(
    val id: Long,
    val username: String,
    val email: String,
    val createdAt: LocalDateTime,
    val totalBookings: Long
)