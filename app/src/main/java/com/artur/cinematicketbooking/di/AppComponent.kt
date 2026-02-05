package com.ivan.cinematicketbooking.di

import com.ivan.cinematicketbooking.navigation.NavigationModule
import com.ivan.cinematicketbooking.presentation.MainActivity
import com.ivan.core.di.CoreModule
import com.ivan.core.di.ViewModelFactoryProvider
import com.ivan.features_auth.di.AuthModule
import com.ivan.features_booking.di.BookingModule
import com.ivan.features_home.di.HomeModule
import com.ivan.features_profile.di.ProfileModule
import com.ivan.network.di.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    CoreModule::class,
    NetworkModule::class,
    AuthModule::class,
    HomeModule::class,
    BookingModule::class,
    ProfileModule::class,
    NavigationModule::class
])
interface AppComponent : ViewModelFactoryProvider {

    @Component.Factory
    interface Factory {
        fun create(@dagger.BindsInstance application: App): AppComponent
    }

    fun inject(app: App)
    fun inject(activity: MainActivity)
}