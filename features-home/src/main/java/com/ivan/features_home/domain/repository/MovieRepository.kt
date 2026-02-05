package com.ivan.cinematicketbooking.features_home.domain.repository

import com.ivan.cinematicketbooking.features_home.domain.model.Movie

interface MovieRepository {
    suspend fun getAllMovies(): List<Movie>
    suspend fun getMovieById(id: Long): Movie
    suspend fun searchMovies(query: String): List<Movie>
    suspend fun getTopMovies(): List<Movie>
    suspend fun getUpcomingMovies(): List<Movie>
    suspend fun getBanners(): List<Movie>
}