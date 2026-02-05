package com.ivan.features_booking.di

import androidx.lifecycle.ViewModel
import com.ivan.cinematicketbooking.features_booking.data.api.BookingApi
import com.ivan.cinematicketbooking.features_booking.data.repository.BookingRepositoryImpl
import com.ivan.cinematicketbooking.features_booking.domain.repository.BookingRepository
import com.ivan.cinematicketbooking.features_booking.presentation.viewmodel.BookingViewModel
import com.ivan.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module
abstract class BookingModule {

    @Binds
    abstract fun bindBookingRepository(impl: BookingRepositoryImpl): BookingRepository

    @Binds
    @IntoMap
    @ViewModelKey(BookingViewModel::class)
    abstract fun bindBookingViewModel(viewModel: BookingViewModel): ViewModel

    companion object {
        @Provides
        fun provideBookingApi(retrofit: Retrofit): BookingApi {
            return retrofit.create(BookingApi::class.java)
        }
    }
}