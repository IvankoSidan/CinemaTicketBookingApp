package com.ivan.cinematicketbooking.features_booking.data.mapper

import com.ivan.cinematicketbooking.features_booking.data.model.BookingResponseDto
import com.ivan.cinematicketbooking.features_booking.data.model.SeatDto
import com.ivan.cinematicketbooking.features_booking.domain.model.Booking
import com.ivan.cinematicketbooking.features_booking.domain.model.BookingStatus
import com.ivan.cinematicketbooking.features_booking.domain.model.Seat
import com.ivan.cinematicketbooking.features_booking.domain.model.SeatStatus

fun SeatDto.toDomain(): Seat {
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

fun BookingResponseDto.toDomain() = Booking(
    id = id,
    userId = userId,
    movieId = movieId,
    movieTitle = movieTitle,
    bookingTime = bookingTime,
    showTime = showTime,
    seatNumber = seatNumber,
    price = price,
    status = try {
        BookingStatus.valueOf(status)
    } catch (e: Exception) {
        BookingStatus.PENDING
    }
)