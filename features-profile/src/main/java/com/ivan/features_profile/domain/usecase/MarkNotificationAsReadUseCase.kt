package com.ivan.features_profile.domain.usecase

import com.ivan.features_profile.domain.model.Notification
import com.ivan.features_profile.domain.repository.NotificationRepository
import javax.inject.Inject

class MarkNotificationAsReadUseCase @Inject constructor(private val repository: NotificationRepository) {
    suspend operator fun invoke(notificationId: Long): Notification =
        repository.markAsRead(notificationId)
}