package com.artur.cinematicketbooking.domain.usecase

import com.artur.cinematicketbooking.domain.model.Movie
import com.artur.cinematicketbooking.domain.repository.MovieRepository

class GetMovieByIdUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(id: Long): Movie = repository.getMovieById(id)
}