package com.ivan.features_profile.domain.model

import java.time.LocalDateTime

data class Notification(
    val id: Long,
    val title: String,
    val message: String,
    val type: String,
    val isRead: Boolean,
    val createdAt: LocalDateTime,
    val relatedId: Long? = null
)