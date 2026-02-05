package com.ivan.cinematicketbooking.features_home.data.repository

import com.ivan.cinematicketbooking.features_home.data.api.MovieApi
import com.ivan.cinematicketbooking.features_home.data.mapper.toDomain
import com.ivan.cinematicketbooking.features_home.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api: MovieApi) : MovieRepository {
    override suspend fun getAllMovies() = api.getAllMovies().map { it.toDomain() }
    override suspend fun getTopMovies() = api.getTopMovies().map { it.toDomain() }
    override suspend fun getUpcomingMovies() = api.getUpcomingMovies().map { it.toDomain() }
    override suspend fun searchMovies(query: String) = api.searchMovies(query).map { it.toDomain() }
    override suspend fun getMovieById(id: Long) = api.getMovieById(id).toDomain()
    override suspend fun getBanners() = api.getBanners().map { it.toDomain() }
}