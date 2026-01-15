package com.artur.cinematicketbooking.domain.usecase

import com.artur.cinematicketbooking.domain.model.Movie
import com.artur.cinematicketbooking.domain.repository.MovieRepository

class GetTopMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(limit: kotlin.Int): List<Movie> = repository.getTopMovies()
}