package com.artur.cinematicketbooking.data.model

import java.math.BigDecimal

data class MovieDto(
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
    val cast: List<CastDto>
)