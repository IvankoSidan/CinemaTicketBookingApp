package com.artur.cinematicketbooking.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artur.cinematicketbooking.R
import com.artur.cinematicketbooking.presentation.factories.ViewModelFactory
import com.artur.cinematicketbooking.data.api.*
import com.artur.cinematicketbooking.data.repository.*
import com.artur.cinematicketbooking.domain.usecase.*

class MainActivity : AppCompatActivity() {

    private val authRepository = AuthRepositoryImpl(RetrofitInstance.authApi)
    private val movieRepository = MovieRepositoryImpl(RetrofitInstance.movieApi)
    private val bookingRepository = BookingRepositoryImpl(RetrofitInstance.bookingApi)
    private val notificationRepository = NotificationRepositoryImpl(RetrofitInstance.notificationApi)
    private val userRepository = UserRepositoryImpl(RetrofitInstance.userApi)
    private val bannerRepository = BannerRepositoryImpl(RetrofitInstance.bannerApi)

    val viewModelFactory by lazy {
        ViewModelFactory(
            loginUseCase = LoginUseCase(authRepository),
            registerUseCase = RegisterUseCase(authRepository),
            getAllMoviesUseCase = GetAllMoviesUseCase(movieRepository),
            getTopMoviesUseCase = GetTopMoviesUseCase(movieRepository),
            getUpcomingMoviesUseCase = GetUpcomingMoviesUseCase(movieRepository),
            getBannersUseCase = GetBannersUseCase(bannerRepository),
            getMovieByIdUseCase = GetMovieByIdUseCase(movieRepository),
            createBookingUseCase = CreateBookingUseCase(bookingRepository),
            getUserBookingsUseCase = GetUserBookingsUseCase(bookingRepository),
            cancelBookingUseCase = CancelBookingUseCase(bookingRepository),
            getNotificationsUseCase = GetNotificationsUseCase(notificationRepository),
            getUnreadNotificationsUseCase = GetUnreadNotificationsUseCase(notificationRepository),
            markNotificationAsReadUseCase = MarkNotificationAsReadUseCase(notificationRepository),
            getUserProfileUseCase = GetUserProfileUseCase(userRepository),
            clearSessionUseCase = ClearSessionUseCase(this),
            getSeatStatusesUseCase = GetSeatStatusesUseCase(bookingRepository)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}



