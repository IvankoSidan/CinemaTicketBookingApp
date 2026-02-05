package com.ivan.cinematicketbooking.features_booking.domain.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Booking(
    val id: Long,
    val userId: Long,
    val movieId: Long,
    val movieTitle: String,
    val bookingTime: LocalDateTime,
    val showTime: LocalDateTime,
    val seatNumber: String,
    val price: BigDecimal,
    val status: BookingStatus
)