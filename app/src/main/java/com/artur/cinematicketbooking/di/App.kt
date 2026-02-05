package com.ivan.cinematicketbooking.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.ivan.cinematicketbooking.nagivation.AppNavigator
import com.ivan.cinematicketbooking.nagivation.NavigatorProvider
import com.ivan.core.di.ViewModelFactoryProvider
import javax.inject.Inject

class App : Application(), ViewModelFactoryProvider, NavigatorProvider {
    lateinit var appComponent: AppComponent

    @Inject
    lateinit var navigator: AppNavigator

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.inject(this)
    }

    override fun provideViewModelFactory(): ViewModelProvider.Factory {
        return appComponent.provideViewModelFactory()
    }

    override fun provideNavigator(): AppNavigator {
        return navigator
    }
}