package com.artur.cinematicketbooking.common

import android.content.res.Resources
import com.artur.cinematicketbooking.data.model.*
import com.artur.cinematicketbooking.domain.model.*
import java.time.LocalDateTime

fun AuthResponseDto.toDomain() =
    User(
        id = 0,
        username = username,
        email = email,
        createdAt = LocalDateTime.now(),
        totalBookings = 0
    )

fun MovieDto.toDomain() =
    Movie(
        id = id,
        title = title,
        description = description,
        imdbRating = imdbRating,
        year = year,
        duration = duration,
        posterUrl = posterUrl,
        trailerUrl = trailerUrl,
        price = price,
        genres = genres,
        cast = cast.map { Cast(it.id, it.actorName, it.photoUrl) },
        releaseDate = null
    )

fun BookingResponseDto.toDomain() =
    Booking(
        id = id,
        userId = userId,
        movieId = movieId,
        movieTitle = movieTitle,
        bookingTime = bookingTime,
        showTime = showTime,
        seatNumber = seatNumber,
        price = price,
        status = BookingStatus.valueOf(status)
    )

fun NotificationDto.toDomain() =
    Notification(
        id = id,
        title = title,
        message = message,
        type = type ?: "info",
        isRead = isRead,
        createdAt = createdAt,
        relatedId = null
    )

fun UserProfileDto.toDomain() =
    User(
        id = id,
        username = username,
        email = email,
        createdAt = createdAt,
        totalBookings = totalBookings
    )

fun BannerDto.toDomain(): Banner =
    Banner(
        id = id,
        name = name,
        ageRating = ageRating,
        genre = genre,
        imageUrl = imageUrl,
        year = year,
        duration = duration,
        movieId = movieId,
        movieTitle = movieTitle,
        posterUrl = posterUrl
    )
fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun SeatDto.toSeat(): Seat {
    val match = Regex("([A-Z])(\\d+)").find(seatNumber)
    val rowChar = match?.groupValues?.get(1)?.firstOrNull() ?: row
    val colNum = match?.groupValues?.get(2)?.toIntOrNull() ?: column

    return Seat(
        number = seatNumber,
        row = rowChar,
        column = colNum,
        status = when (status) {
            "AVAILABLE" -> SeatStatus.AVAILABLE
            "UNAVAILABLE" -> SeatStatus.UNAVAILABLE
            else -> SeatStatus.UNAVAILABLE
        },
        price = price
    )
}
