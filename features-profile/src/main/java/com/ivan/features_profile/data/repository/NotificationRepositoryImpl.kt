package com.ivan.features_profile.data.repository

import com.ivan.features_profile.data.api.NotificationApi
import com.ivan.features_profile.data.mapper.toDomain
import com.ivan.features_profile.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(private val api: NotificationApi) : NotificationRepository {
    override suspend fun getUserNotifications() =
        api.getUserNotifications().map { it.toDomain() }

    override suspend fun getUnreadNotifications() =
        api.getUnreadNotifications().map { it.toDomain() }

    override suspend fun markAsRead(notificationId: Long) =
        api.markAsRead(notificationId).toDomain()
}
