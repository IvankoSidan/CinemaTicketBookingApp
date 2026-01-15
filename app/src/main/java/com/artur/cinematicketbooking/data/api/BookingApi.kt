package com.artur.cinematicketbooking.data.api

import com.artur.cinematicketbooking.data.model.BookingRequestDto
import com.artur.cinematicketbooking.data.model.BookingResponseDto
import com.artur.cinematicketbooking.data.model.SeatDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BookingApi {
    @POST("/api/bookings")
    suspend fun createBooking(@Body request: BookingRequestDto): BookingResponseDto

    @GET("/api/bookings/my")
    suspend fun getUserBookings(): List<BookingResponseDto>

    @DELETE("/api/bookings/{id}")
    suspend fun cancelBooking(@Path("id") bookingId: Long)

    @GET("/api/bookings/seats/status")
    suspend fun getSeatStatuses(
        @Query("movieId") movieId: Long,
        @Query("showTime") showTime: String
    ): List<SeatDto>
}