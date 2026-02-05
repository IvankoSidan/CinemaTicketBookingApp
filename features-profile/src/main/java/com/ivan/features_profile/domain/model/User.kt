package com.ivan.features_profile.domain.model

import java.time.LocalDateTime

data class User(
    val id: Long,
    val username: String,
    val email: String,
    val createdAt: LocalDateTime,
    val totalBookings: Long
)