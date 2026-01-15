package com.artur.cinematicketbooking.data.model

import com.artur.cinematicketbooking.domain.model.Seat
import com.artur.cinematicketbooking.domain.model.SeatStatus

data class SeatDto(
    val seatNumber: String,
    val row: Char,
    val column: Int,
    val status: String,
    val price: Double
) {
    fun toSeat(): Seat {
        return Seat(
            number = seatNumber,
            row = row,
            column = column,
            status = when (status) {
                "AVAILABLE" -> SeatStatus.AVAILABLE
                "UNAVAILABLE" -> SeatStatus.UNAVAILABLE
                else -> SeatStatus.UNAVAILABLE
            },
            price = price
        )
    }
}