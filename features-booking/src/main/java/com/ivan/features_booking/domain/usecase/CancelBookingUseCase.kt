package com.ivan.cinematicketbooking.features_booking.domain.usecase

import com.ivan.cinematicketbooking.features_booking.domain.repository.BookingRepository
import javax.inject.Inject


class CancelBookingUseCase @Inject constructor(private val repository: BookingRepository) {
    suspend operator fun invoke(bookingId: Long) = repository.cancelBooking(bookingId)
}