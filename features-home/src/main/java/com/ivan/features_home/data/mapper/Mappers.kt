package com.ivan.cinematicketbooking.features_home.data.mapper

import com.ivan.cinematicketbooking.features_home.data.model.BannerDto
import com.ivan.cinematicketbooking.features_home.data.model.MovieDto
import com.ivan.cinematicketbooking.features_home.domain.model.Banner
import com.ivan.cinematicketbooking.features_home.domain.model.Cast
import com.ivan.cinematicketbooking.features_home.domain.model.Movie

fun MovieDto.toDomain() = Movie(
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

fun BannerDto.toDomain() = Banner(
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
