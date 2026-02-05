package com.ivan.cinematicketbooking.features_home.domain.model

import java.math.BigDecimal
import java.time.LocalDate

data class Movie(
    val id: Long,
    val title: String,
    val description: String?,
    val imdbRating: BigDecimal?,
    val year: Int?,
    val duration: Int?,
    val posterUrl: String?,
    val trailerUrl: String?,
    val price: BigDecimal?,
    val genres: List<String>,
    val cast: List<Cast>,
    val releaseDate: LocalDate?
)
