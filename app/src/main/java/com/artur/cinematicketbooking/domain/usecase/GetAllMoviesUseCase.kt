package com.artur.cinematicketbooking.domain.usecase

import com.artur.cinematicketbooking.domain.model.Movie
import com.artur.cinematicketbooking.domain.repository.MovieRepository

class GetAllMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(): List<Movie> = repository.getAllMovies()
}