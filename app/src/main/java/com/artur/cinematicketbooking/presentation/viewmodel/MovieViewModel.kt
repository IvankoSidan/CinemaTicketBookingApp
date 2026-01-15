package com.artur.cinematicketbooking.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artur.cinematicketbooking.domain.model.Banner
import com.artur.cinematicketbooking.domain.model.Movie
import com.artur.cinematicketbooking.domain.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class MovieViewModel(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val getTopMoviesUseCase: GetTopMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getBannersUseCase: GetBannersUseCase,
    private val getMovieByIdUseCase: GetMovieByIdUseCase
) : ViewModel() {

    private val _allMovies = MutableStateFlow<List<Movie>>(emptyList())
    val allMovies: StateFlow<List<Movie>> = _allMovies

    private val _topMovies = MutableStateFlow<List<Movie>>(emptyList())
    val topMovies: StateFlow<List<Movie>> = _topMovies

    private val _upcomingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val upcomingMovies: StateFlow<List<Movie>> = _upcomingMovies

    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults

    private val _banners = MutableStateFlow<List<Banner>>(emptyList())
    val banners: StateFlow<List<Banner>> = _banners

    private val _selectedMovieId = MutableStateFlow<Long?>(null)
    val selectedMovieId: StateFlow<Long?> = _selectedMovieId

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        loadAllMovies()
        loadTopMovies()
        loadUpcomingMovies()
    }

    fun loadAllMovies() = viewModelScope.launch {
        val movies = getAllMoviesUseCase()
        _allMovies.value = movies
    }

    fun loadTopMovies(limit: Int = 10) = viewModelScope.launch {
        _topMovies.value = getTopMoviesUseCase.invoke(limit)
    }

    fun loadUpcomingMovies() = viewModelScope.launch {
        _upcomingMovies.value = getUpcomingMoviesUseCase()
    }

    fun loadBanners() = viewModelScope.launch {
        _banners.value = getBannersUseCase()
    }

    fun searchMovies(query: String) {
        _searchQuery.value = query
        _searchResults.value = if (query.isBlank()) {
            emptyList()
        } else {
            _allMovies.value.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.description?.contains(query, ignoreCase = true) == true
            }
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _searchResults.value = emptyList()
    }

    fun selectMovie(id: Long) {
        _selectedMovieId.value = id
    }

    fun getMovieById(id: Long, onResult: (Movie) -> Unit) = viewModelScope.launch {
        val local = _allMovies.value.find { it.id == id }
        onResult(local ?: getMovieByIdUseCase(id))
    }
}
