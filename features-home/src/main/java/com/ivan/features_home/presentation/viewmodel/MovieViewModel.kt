package com.ivan.cinematicketbooking.presentation.viewmodel

import com.ivan.cinematicketbooking.core.base.BaseViewModel
import com.ivan.cinematicketbooking.core.base.NavigationEvent
import com.ivan.cinematicketbooking.features_home.domain.model.Banner
import com.ivan.cinematicketbooking.features_home.domain.model.Movie
import com.ivan.cinematicketbooking.features_home.domain.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val getTopMoviesUseCase: GetTopMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getBannersUseCase: GetBannersUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val getMovieByIdUseCase: GetMovieByIdUseCase
) : BaseViewModel() {

    private val _topMovies = MutableStateFlow<List<Movie>>(emptyList())
    val topMovies: StateFlow<List<Movie>> = _topMovies

    private val _upcomingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val upcomingMovies: StateFlow<List<Movie>> = _upcomingMovies

    private val _banners = MutableStateFlow<List<Banner>>(emptyList())
    val banners: StateFlow<List<Banner>> = _banners

    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        launch { _topMovies.value = getTopMoviesUseCase() }
        launch { _upcomingMovies.value = getUpcomingMoviesUseCase() }
        launch { _banners.value = getBannersUseCase() }
    }

    fun search(query: String) {
        launch {
            _searchResults.value = if (query.isBlank()) emptyList() else searchMoviesUseCase(query)
        }
    }

    fun onMovieClicked(movieId: Long) {
        launch {
            _selectedMovie.value = getMovieByIdUseCase(movieId)
            navigate(NavigationEvent.ToDetails)
        }
    }
}