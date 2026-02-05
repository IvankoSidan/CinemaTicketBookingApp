package com.ivan.cinematicketbooking.navigation

import com.ivan.cinematicketbooking.nagivation.AppNavigator
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class NavigationModule {

    @Binds
    @Singleton
    abstract fun bindAppNavigator(impl: AppNavigatorImpl): AppNavigator
}