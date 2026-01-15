package com.artur.cinematicketbooking.domain.repository

import com.artur.cinematicketbooking.domain.model.Booking
import com.artur.cinematicketbooking.domain.model.Seat
import java.time.LocalDateTime

interface BookingRepository {
    suspend fun createBooking(movieId: Long, seatNumber: String, price: Double, showTime: LocalDateTime): Booking
    suspend fun getUserBookings(): List<Booking>
    suspend fun cancelBooking(bookingId: Long)
    suspend fun getSeatStatuses(movieId: Long, showTime: String): List<Seat>
}