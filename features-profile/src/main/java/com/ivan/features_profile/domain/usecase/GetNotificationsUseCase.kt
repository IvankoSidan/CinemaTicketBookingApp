package com.ivan.features_profile.domain.usecase

import com.ivan.features_profile.domain.model.Notification
import com.ivan.features_profile.domain.repository.NotificationRepository
import javax.inject.Inject


class GetNotificationsUseCase @Inject constructor(private val repository: NotificationRepository) {
    suspend operator fun invoke(): List<Notification> = repository.getUserNotifications()
}