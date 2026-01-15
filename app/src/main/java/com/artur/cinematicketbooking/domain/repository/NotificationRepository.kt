package com.artur.cinematicketbooking.domain.repository

import com.artur.cinematicketbooking.domain.model.Notification

interface NotificationRepository {
    suspend fun getUserNotifications(): List<Notification>
    suspend fun getUnreadNotifications(): List<Notification>
    suspend fun markAsRead(notificationId: Long): Notification
}