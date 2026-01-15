package com.artur.cinematicketbooking.domain.usecase

import com.artur.cinematicketbooking.domain.model.Booking
import com.artur.cinematicketbooking.domain.repository.BookingRepository
import java.time.LocalDateTime

class CreateBookingUseCase(private val repository: BookingRepository) {
    suspend operator fun invoke(movieId: Long, seatNumber: String, price: Double, showTime: LocalDateTime): Booking =
        repository.createBooking(movieId, seatNumber, price, showTime)
}