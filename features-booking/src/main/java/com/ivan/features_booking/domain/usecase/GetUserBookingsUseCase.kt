package com.ivan.cinematicketbooking.features_booking.domain.usecase

import com.ivan.cinematicketbooking.features_booking.domain.model.Booking
import com.ivan.cinematicketbooking.features_booking.domain.repository.BookingRepository
import javax.inject.Inject

class GetUserBookingsUseCase @Inject constructor(private val repository: BookingRepository) {
    suspend operator fun invoke(): List<Booking> = repository.getUserBookings()
}