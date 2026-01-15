package com.artur.cinematicketbooking.domain.usecase

import com.artur.cinematicketbooking.domain.model.Seat
import com.artur.cinematicketbooking.domain.repository.BookingRepository

class GetSeatStatusesUseCase(private val repository: BookingRepository) {
    suspend operator fun invoke(movieId: Long, showTime: String): List<Seat> =
        repository.getSeatStatuses(movieId, showTime)
}