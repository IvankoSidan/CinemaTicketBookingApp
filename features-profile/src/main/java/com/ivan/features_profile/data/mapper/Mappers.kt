package com.ivan.features_profile.data.mapper

import com.ivan.features_profile.data.model.NotificationDto
import com.ivan.features_profile.data.model.UserProfileDto
import com.ivan.features_profile.domain.model.Notification
import com.ivan.features_profile.domain.model.User

fun UserProfileDto.toDomain() = User(
    id = id,
    username = username,
    email = email,
    createdAt = createdAt,
    totalBookings = totalBookings
)

fun NotificationDto.toDomain() = Notification(
    id = id,
    title = title,
    message = message,
    type = type ?: "info",
    isRead = isRead,
    createdAt = createdAt,
    relatedId = null
)