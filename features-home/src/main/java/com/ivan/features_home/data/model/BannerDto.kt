package com.ivan.cinematicketbooking.features_home.data.model


data class BannerDto(
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

