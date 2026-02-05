package com.ivan.cinematicketbooking.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow: SharedFlow<String> = _errorFlow.asSharedFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _errorFlow.emit(throwable.message ?: "An unknown error occurred")
        }
    }

    protected fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(exceptionHandler) {
            block()
        }
    }

    protected fun navigate(event: NavigationEvent) {
        viewModelScope.launch { _navigationEvent.emit(event) }
    }
}

sealed class NavigationEvent {
    object ToAuth : NavigationEvent()
    object ToHome : NavigationEvent()
    object ToDetails : NavigationEvent()
    object ToBooking : NavigationEvent()
    object ToNotifications : NavigationEvent()
    object Back : NavigationEvent()
}