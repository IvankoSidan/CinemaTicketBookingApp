package com.artur.cinematicketbooking.domain.repository

import com.artur.cinematicketbooking.domain.model.Movie

interface MovieRepository {
    suspend fun getAllMovies(): List<Movie>
    suspend fun getMovieById(id: Long): Movie
    suspend fun searchMovies(query: String): List<Movie>
    suspend fun getTopMovies(): List<Movie>
    suspend fun getUpcomingMovies(): List<Movie>
    suspend fun getBanners(): List<Movie>
}