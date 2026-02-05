package com.ivan.cinematicketbooking.di

import android.content.Context
import com.ivan.core.util.MutableTokenProvider
import com.ivan.core.util.TokenProvider
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class AppModule {

    @Binds
    abstract fun bindTokenProvider(impl: TokenProviderImpl): TokenProvider

    @Binds
    abstract fun bindMutableTokenProvider(impl: TokenProviderImpl): MutableTokenProvider

    companion object {
        @Provides
        fun provideContext(application: App): Context {
            return application.applicationContext
        }
    }
}