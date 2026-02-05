package com.ivan.cinematicketbooking.features_booking.domain.repository

import com.ivan.cinematicketbooking.features_booking.domain.model.Booking
import com.ivan.cinematicketbooking.features_booking.domain.model.Seat
import java.time.LocalDateTime

interface BookingRepository {
    suspend fun createBooking(movieId: Long, seatNumber: String, price: Double, showTime: LocalDateTime): Booking
    suspend fun getUserBookings(): List<Booking>
    suspend fun cancelBooking(bookingId: Long)
    suspend fun getSeatStatuses(movieId: Long, showTime: String): List<Seat>
}