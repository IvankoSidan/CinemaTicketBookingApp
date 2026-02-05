package com.ivan.cinematicketbooking.nagivation

interface AppNavigator {
    fun navigateToAuth()
    fun navigateToHome()
    fun navigateToMovieDetails()
    fun navigateToBooking()
    fun navigateToNotifications()
    fun navigateToProfile()
    fun goBack()
}
interface NavigatorProvider {
    fun provideNavigator(): AppNavigator
}