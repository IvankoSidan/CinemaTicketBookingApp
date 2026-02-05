package com.ivan.features_profile.domain.repository

import com.ivan.features_profile.domain.model.Notification

interface NotificationRepository {
    suspend fun getUserNotifications(): List<Notification>
    suspend fun getUnreadNotifications(): List<Notification>
    suspend fun markAsRead(notificationId: Long): Notification
}