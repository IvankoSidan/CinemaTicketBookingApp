package com.artur.cinematicketbooking.data.repository

import com.artur.cinematicketbooking.common.toDomain
import com.artur.cinematicketbooking.data.api.NotificationApi
import com.artur.cinematicketbooking.domain.repository.NotificationRepository

class NotificationRepositoryImpl(private val api: NotificationApi) : NotificationRepository {
    override suspend fun getUserNotifications() =
        api.getUserNotifications().map { it.toDomain() }

    override suspend fun getUnreadNotifications() =
        api.getUnreadNotifications().map { it.toDomain() }

    override suspend fun markAsRead(notificationId: Long) =
        api.markAsRead(notificationId).toDomain()
}
