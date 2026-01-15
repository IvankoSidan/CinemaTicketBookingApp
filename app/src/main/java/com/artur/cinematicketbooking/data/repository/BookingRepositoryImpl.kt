package com.artur.cinematicketbooking.data.repository

import com.artur.cinematicketbooking.common.toDomain
import com.artur.cinematicketbooking.data.api.BookingApi
import com.artur.cinematicketbooking.data.model.BookingRequestDto
import com.artur.cinematicketbooking.domain.model.Booking
import com.artur.cinematicketbooking.domain.model.Seat
import com.artur.cinematicketbooking.domain.repository.BookingRepository
import java.time.LocalDateTime

class BookingRepositoryImpl(private val api: BookingApi) : BookingRepository {
    override suspend fun createBooking(movieId: Long, seatNumber: String, price: Double, showTime: LocalDateTime): Booking =
        api.createBooking(BookingRequestDto(movieId, seatNumber, price.toBigDecimal(), showTime)).toDomain()

    override suspend fun getUserBookings() = api.getUserBookings().map { it.toDomain() }

    override suspend fun cancelBooking(bookingId: Long) = api.cancelBooking(bookingId)

    override suspend fun getSeatStatuses(movieId: Long, showTime: String): List<Seat> =
        api.getSeatStatuses(movieId, showTime).map { it.toSeat() }
}