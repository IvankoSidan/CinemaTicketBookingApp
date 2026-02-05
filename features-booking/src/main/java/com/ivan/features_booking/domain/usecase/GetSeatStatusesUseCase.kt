package com.ivan.cinematicketbooking.features_booking.domain.usecase

import com.ivan.cinematicketbooking.features_booking.domain.model.Seat
import com.ivan.cinematicketbooking.features_booking.domain.repository.BookingRepository
import javax.inject.Inject

class GetSeatStatusesUseCase @Inject constructor(private val repository: BookingRepository) {
    suspend operator fun invoke(movieId: Long, showTime: String): List<Seat> =
        repository.getSeatStatuses(movieId, showTime)
}