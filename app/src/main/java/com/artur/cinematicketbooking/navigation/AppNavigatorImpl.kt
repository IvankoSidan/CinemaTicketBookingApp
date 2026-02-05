package com.ivan.cinematicketbooking.navigation

import com.ivan.cinematicketbooking.R
import com.ivan.cinematicketbooking.nagivation.AppNavigator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

sealed class NavigationCommand {
    data class To(val destinationId: Int) : NavigationCommand()
    object Back : NavigationCommand()
}

@Singleton
class AppNavigatorImpl @Inject constructor() : AppNavigator {
    private val _navigationCommands = MutableSharedFlow<NavigationCommand>(extraBufferCapacity = 1)
    val navigationCommands = _navigationCommands.asSharedFlow()

    override fun navigateToAuth() {
        _navigationCommands.tryEmit(NavigationCommand.To(R.id.authFragment))
    }

    override fun navigateToHome() {
        _navigationCommands.tryEmit(NavigationCommand.To(R.id.explorerFragment))
    }

    override fun navigateToMovieDetails() {
        _navigationCommands.tryEmit(NavigationCommand.To(R.id.filmDetailsFragment))
    }

    override fun navigateToBooking() {
        _navigationCommands.tryEmit(NavigationCommand.To(R.id.cartFragment))
    }

    override fun navigateToNotifications() {
        _navigationCommands.tryEmit(NavigationCommand.To(R.id.fragmentNotifications))
    }

    override fun navigateToProfile() {
        _navigationCommands.tryEmit(NavigationCommand.To(R.id.profileFragment))
    }

    override fun goBack() {
        _navigationCommands.tryEmit(NavigationCommand.Back)
    }
}