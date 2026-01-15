package com.artur.cinematicketbooking.domain.usecase

import com.artur.cinematicketbooking.domain.model.Booking
import com.artur.cinematicketbooking.domain.repository.BookingRepository

class GetUserBookingsUseCase(private val repository: BookingRepository) {
    suspend operator fun invoke(): List<Booking> = repository.getUserBookings()
}