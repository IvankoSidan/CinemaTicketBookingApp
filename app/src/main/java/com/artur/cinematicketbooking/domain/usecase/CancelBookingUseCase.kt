package com.artur.cinematicketbooking.domain.usecase

import com.artur.cinematicketbooking.domain.repository.BookingRepository

class CancelBookingUseCase(private val repository: BookingRepository) {
    suspend operator fun invoke(bookingId: Long) = repository.cancelBooking(bookingId)
}