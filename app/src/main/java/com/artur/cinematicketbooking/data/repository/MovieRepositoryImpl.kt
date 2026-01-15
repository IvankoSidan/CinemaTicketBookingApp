package com.artur.cinematicketbooking.data.repository

import com.artur.cinematicketbooking.common.toDomain
import com.artur.cinematicketbooking.data.api.MovieApi
import com.artur.cinematicketbooking.domain.repository.MovieRepository

class MovieRepositoryImpl(private val api: MovieApi) : MovieRepository {
    override suspend fun getAllMovies() = api.getAllMovies().map { it.toDomain() }
    override suspend fun getMovieById(id: Long) = api.getMovieById(id).toDomain()
    override suspend fun searchMovies(query: String) = api.searchMovies(query).map { it.toDomain() }
    override suspend fun getTopMovies() = api.getTopMovies().map { it.toDomain() }
    override suspend fun getUpcomingMovies() = api.getUpcomingMovies().map { it.toDomain() }
    override suspend fun getBanners() = api.getBanners().map { it.toDomain() }
}