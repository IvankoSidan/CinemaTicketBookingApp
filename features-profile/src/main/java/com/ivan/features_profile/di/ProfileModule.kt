package com.ivan.features_profile.di

import androidx.lifecycle.ViewModel
import com.ivan.core.di.ViewModelKey
import com.ivan.features_profile.data.api.NotificationApi
import com.ivan.features_profile.data.api.UserApi
import com.ivan.features_profile.data.repository.NotificationRepositoryImpl
import com.ivan.features_profile.data.repository.UserRepositoryImpl
import com.ivan.features_profile.domain.repository.NotificationRepository
import com.ivan.features_profile.domain.repository.UserRepository
import com.ivan.features_profile.presentation.viewmodel.NotificationViewModel
import com.ivan.features_profile.presentation.viewmodel.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module
abstract class ProfileModule {

    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindNotificationRepository(impl: NotificationRepositoryImpl): NotificationRepository

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotificationViewModel::class)
    abstract fun bindNotificationViewModel(viewModel: NotificationViewModel): ViewModel

    companion object {
        @Provides
        fun provideUserApi(retrofit: Retrofit): UserApi {
            return retrofit.create(UserApi::class.java)
        }

        @Provides
        fun provideNotificationApi(retrofit: Retrofit): NotificationApi {
            return retrofit.create(NotificationApi::class.java)
        }
    }
}