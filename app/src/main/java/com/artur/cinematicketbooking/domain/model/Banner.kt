package com.artur.cinematicketbooking.domain.model

data class Banner(
    val id: Long,
    val name: String?,
    val ageRating: String?,
    val genre: String?,
    val imageUrl: String?,
    val year: Int?,
    val duration: Int?,
    val movieId: Long?,
    val movieTitle: String?,
    val posterUrl: String?
)
