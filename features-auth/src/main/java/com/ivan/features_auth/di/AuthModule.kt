package com.ivan.features_auth.di

import androidx.lifecycle.ViewModel
import com.ivan.cinematicketbooking.features_auth.data.api.AuthApi
import com.ivan.cinematicketbooking.features_auth.data.repository.AuthRepositoryImpl
import com.ivan.cinematicketbooking.features_auth.domain.repository.AuthRepository
import com.ivan.core.di.ViewModelKey
import com.ivan.features_auth.presentation.viewmodel.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module
abstract class AuthModule {

    @Binds
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

    companion object {
        @Provides
        fun provideAuthApi(retrofit: Retrofit): AuthApi {
            return retrofit.create(AuthApi::class.java)
        }
    }
}