package com.ivan.cinematicketbooking.features_booking.domain.usecase

import com.ivan.cinematicketbooking.features_booking.domain.model.Booking // Исправлен импорт
import com.ivan.cinematicketbooking.features_booking.domain.repository.BookingRepository
import java.time.LocalDateTime
import javax.inject.Inject

class CreateBookingUseCase @Inject constructor(private val repository: BookingRepository) {
    suspend operator fun invoke(movieId: Long, seatNumber: String, price: Double, showTime: LocalDateTime): Booking =
        repository.createBooking(movieId, seatNumber, price, showTime)
}