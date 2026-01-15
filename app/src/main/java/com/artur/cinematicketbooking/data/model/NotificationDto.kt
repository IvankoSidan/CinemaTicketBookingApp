package com.artur.cinematicketbooking.data.model

import java.time.LocalDateTime

data class NotificationDto(
    val id: Long,
    val userId: Long,
    val title: String,
    val message: String,
    val type: String?,
    val createdAt: LocalDateTime,
    val isRead: Boolean
)
