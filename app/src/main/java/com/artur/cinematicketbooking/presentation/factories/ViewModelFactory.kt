package com.artur.cinematicketbooking.presentation.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.artur.cinematicketbooking.domain.usecase.CancelBookingUseCase
import com.artur.cinematicketbooking.domain.usecase.ClearSessionUseCase
import com.artur.cinematicketbooking.domain.usecase.CreateBookingUseCase
import com.artur.cinematicketbooking.domain.usecase.GetAllMoviesUseCase
import com.artur.cinematicketbooking.domain.usecase.GetBannersUseCase
import com.artur.cinematicketbooking.domain.usecase.GetMovieByIdUseCase
import com.artur.cinematicketbooking.domain.usecase.GetNotificationsUseCase
import com.artur.cinematicketbooking.domain.usecase.GetSeatStatusesUseCase
import com.artur.cinematicketbooking.domain.usecase.GetTopMoviesUseCase
import com.artur.cinematicketbooking.domain.usecase.GetUnreadNotificationsUseCase
import com.artur.cinematicketbooking.domain.usecase.GetUpcomingMoviesUseCase
import com.artur.cinematicketbooking.domain.usecase.GetUserBookingsUseCase
import com.artur.cinematicketbooking.domain.usecase.GetUserProfileUseCase
import com.artur.cinematicketbooking.domain.usecase.LoginUseCase
import com.artur.cinematicketbooking.domain.usecase.MarkNotificationAsReadUseCase
import com.artur.cinematicketbooking.domain.usecase.RegisterUseCase
import com.artur.cinematicketbooking.domain.usecase.SearchMoviesUseCase
import com.artur.cinematicketbooking.presentation.viewmodel.AuthViewModel
import com.artur.cinematicketbooking.presentation.viewmodel.BookingViewModel
import com.artur.cinematicketbooking.presentation.viewmodel.MovieViewModel
import com.artur.cinematicketbooking.presentation.viewmodel.NotificationViewModel
import com.artur.cinematicketbooking.presentation.viewmodel.ProfileViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val getTopMoviesUseCase: GetTopMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getBannersUseCase: GetBannersUseCase,
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val createBookingUseCase: CreateBookingUseCase,
    private val getUserBookingsUseCase: GetUserBookingsUseCase,
    private val cancelBookingUseCase: CancelBookingUseCase,
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val getUnreadNotificationsUseCase: GetUnreadNotificationsUseCase,
    private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val clearSessionUseCase: ClearSessionUseCase,
    private val getSeatStatusesUseCase: GetSeatStatusesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) ->
                AuthViewModel(loginUseCase, registerUseCase) as T

            modelClass.isAssignableFrom(MovieViewModel::class.java) ->
                MovieViewModel(
                    getAllMoviesUseCase,
                    getTopMoviesUseCase,
                    getUpcomingMoviesUseCase,
                    getBannersUseCase,
                    getMovieByIdUseCase
                ) as T

            modelClass.isAssignableFrom(BookingViewModel::class.java) ->
                BookingViewModel(
                    createBookingUseCase,
                    getUserBookingsUseCase,
                    cancelBookingUseCase,
                    getSeatStatusesUseCase
                ) as T

            modelClass.isAssignableFrom(NotificationViewModel::class.java) ->
                NotificationViewModel(
                    getNotificationsUseCase,
                    getUnreadNotificationsUseCase,
                    markNotificationAsReadUseCase
                ) as T

            modelClass.isAssignableFrom(ProfileViewModel::class.java) ->
                ProfileViewModel(getUserProfileUseCase, clearSessionUseCase) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}