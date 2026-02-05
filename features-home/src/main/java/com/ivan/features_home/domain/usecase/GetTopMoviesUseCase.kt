package com.ivan.cinematicketbooking.features_home.domain.usecase

import com.ivan.cinematicketbooking.features_home.domain.model.Movie
import com.ivan.cinematicketbooking.features_home.domain.repository.MovieRepository
import javax.inject.Inject

class GetTopMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(): List<Movie> =
        repository.getTopMovies()
}
