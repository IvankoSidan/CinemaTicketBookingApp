package com.ivan.cinematicketbooking.features_booking.data.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class BookingRequestDto(
    val movieId: Long,
    val seatNumber: String,
    val price: BigDecimal,
    val showTime: LocalDateTime
)