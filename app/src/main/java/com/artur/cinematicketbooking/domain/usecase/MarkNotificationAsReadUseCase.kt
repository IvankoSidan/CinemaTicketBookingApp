package com.artur.cinematicketbooking.domain.usecase

import com.artur.cinematicketbooking.domain.model.Notification
import com.artur.cinematicketbooking.domain.repository.NotificationRepository

class MarkNotificationAsReadUseCase(private val repository: NotificationRepository) {
    suspend operator fun invoke(notificationId: Long): Notification =
        repository.markAsRead(notificationId)
}