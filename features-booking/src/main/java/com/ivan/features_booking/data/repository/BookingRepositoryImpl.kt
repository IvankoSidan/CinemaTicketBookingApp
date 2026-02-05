package com.ivan.cinematicketbooking.features_booking.data.repository

import com.ivan.cinematicketbooking.features_booking.data.api.BookingApi
import com.ivan.cinematicketbooking.features_booking.data.mapper.toDomain
import com.ivan.cinematicketbooking.features_booking.data.model.BookingRequestDto
import com.ivan.cinematicketbooking.features_booking.domain.model.Booking
import com.ivan.cinematicketbooking.features_booking.domain.model.Seat
import com.ivan.cinematicketbooking.features_booking.domain.repository.BookingRepository
import java.time.LocalDateTime
import javax.inject.Inject

class BookingRepositoryImpl @Inject constructor(
    private val api: BookingApi
) : BookingRepository {

    override suspend fun createBooking(
        movieId: Long,
        seatNumber: String,
        price: Double,
        showTime: LocalDateTime
    ): Booking = api.createBooking(
        BookingRequestDto(
            movieId,
            seatNumber,
            price.toBigDecimal(),
            showTime
        )
    ).toDomain()

    override suspend fun getUserBookings(): List<Booking> {
        return api.getUserBookings().map { it.toDomain() }
    }

    override suspend fun cancelBooking(bookingId: Long) {
        api.cancelBooking(bookingId)
    }

    override suspend fun getSeatStatuses(movieId: Long, showTime: String): List<Seat> =
        api.getSeatStatuses(movieId, showTime).map { it.toDomain() }
}