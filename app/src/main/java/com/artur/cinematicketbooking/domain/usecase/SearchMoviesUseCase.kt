package com.artur.cinematicketbooking.domain.usecase

import com.artur.cinematicketbooking.domain.model.Movie
import com.artur.cinematicketbooking.domain.repository.MovieRepository

class SearchMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(query: String): List<Movie> =
        if (query.isNotEmpty()) repository.searchMovies(query) else emptyList()
}