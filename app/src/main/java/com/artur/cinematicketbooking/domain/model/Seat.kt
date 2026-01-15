package com.artur.cinematicketbooking.domain.model

data class Seat(
    val number: String,
    val row: Char,
    val column: Int,
    val status: SeatStatus,
    val price: Double
)

enum class SeatStatus {
    AVAILABLE, SELECTED, UNAVAILABLE
}