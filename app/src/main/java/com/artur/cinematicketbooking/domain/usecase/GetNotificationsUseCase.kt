package com.artur.cinematicketbooking.domain.usecase

import com.artur.cinematicketbooking.domain.model.Notification
import com.artur.cinematicketbooking.domain.repository.NotificationRepository

class GetNotificationsUseCase(private val repository: NotificationRepository) {
    suspend operator fun invoke(): List<Notification> = repository.getUserNotifications()
}