package com.ivan.cinematicketbooking.features_booking.data.api

import com.ivan.cinematicketbooking.features_booking.data.model.BookingRequestDto
import com.ivan.cinematicketbooking.features_booking.data.model.BookingResponseDto
import com.ivan.cinematicketbooking.features_booking.data.model.SeatDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BookingApi {
    @POST("bookings")
    suspend fun createBooking(@Body request: BookingRequestDto): BookingResponseDto

    @GET("bookings/my")
    suspend fun getUserBookings(): List<BookingResponseDto>

    @DELETE("bookings/{id}")
    suspend fun cancelBooking(@Path("id") bookingId: Long)

    @GET("bookings/seats/status")
    suspend fun getSeatStatuses(
        @Query("movieId") movieId: Long,
        @Query("showTime") showTime: String
    ): List<SeatDto>
}