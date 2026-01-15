package com.artur.cinematicketbooking.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artur.cinematicketbooking.domain.model.Notification
import com.artur.cinematicketbooking.domain.usecase.GetNotificationsUseCase
import com.artur.cinematicketbooking.domain.usecase.GetUnreadNotificationsUseCase
import com.artur.cinematicketbooking.domain.usecase.MarkNotificationAsReadUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val getUnreadNotificationsUseCase: GetUnreadNotificationsUseCase,
    private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase
) : ViewModel() {

    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications

    private val _unreadCount = MutableStateFlow(0)
    val unreadCount: StateFlow<Int> = _unreadCount

    private enum class Filter { ALL, UNREAD }
    private var currentFilter = Filter.ALL

    fun loadAll() = viewModelScope.launch {
        currentFilter = Filter.ALL
        _notifications.value = getNotificationsUseCase()
    }

    fun loadUnread() = viewModelScope.launch {
        currentFilter = Filter.UNREAD
        _notifications.value = getUnreadNotificationsUseCase()
    }

    fun loadUnreadCount() = viewModelScope.launch {
        _unreadCount.value = getUnreadNotificationsUseCase().size
    }

    fun markAsRead(id: Long) = viewModelScope.launch {
        markNotificationAsReadUseCase(id)
        when (currentFilter) {
            Filter.ALL -> loadAll()
            Filter.UNREAD -> loadUnread()
        }
        loadUnreadCount()
    }
}