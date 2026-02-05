package com.ivan.features_profile.presentation.viewmodel

import com.ivan.cinematicketbooking.core.base.BaseViewModel
import com.ivan.cinematicketbooking.core.base.NavigationEvent
import com.ivan.features_profile.domain.model.Notification
import com.ivan.features_profile.domain.usecase.GetNotificationsUseCase
import com.ivan.features_profile.domain.usecase.GetUnreadNotificationsUseCase
import com.ivan.features_profile.domain.usecase.MarkNotificationAsReadUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NotificationViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val getUnreadNotificationsUseCase: GetUnreadNotificationsUseCase,
    private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase
) : BaseViewModel() {

    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications

    private val _unreadCount = MutableStateFlow(0)
    val unreadCount: StateFlow<Int> = _unreadCount

    fun loadAll() {
        launch { _notifications.value = getNotificationsUseCase() }
    }

    fun loadUnread() {
        launch { _notifications.value = getUnreadNotificationsUseCase() }
    }

    fun loadUnreadCount() {
        launch { _unreadCount.value = getUnreadNotificationsUseCase().size }
    }

    fun markAsRead(id: Long) {
        launch {
            markNotificationAsReadUseCase(id)
            loadAll()
            loadUnreadCount()
        }
    }

    fun goBack() = navigate(NavigationEvent.Back)
}