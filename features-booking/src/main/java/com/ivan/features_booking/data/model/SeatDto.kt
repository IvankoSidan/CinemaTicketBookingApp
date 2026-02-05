package com.ivan.cinematicketbooking.features_booking.data.model

data class SeatDto(
    val seatNumber: String,
    val row: Char,
    val column: Int,
    val status: String,
    val price: Double
)