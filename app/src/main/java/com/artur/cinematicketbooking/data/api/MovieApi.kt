package com.artur.cinematicketbooking.data.api

import com.artur.cinematicketbooking.data.model.MovieDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("/api/movies")
    suspend fun getAllMovies(): List<MovieDto>

    @GET("/api/movies/{id}")
    suspend fun getMovieById(@Path("id") id: Long): MovieDto

    @GET("/api/movies/search")
    suspend fun searchMovies(@Query("query") query: String): List<MovieDto>

    @GET("/api/movies/top")
    suspend fun getTopMovies(): List<MovieDto>

    @GET("/api/movies/upcoming")
    suspend fun getUpcomingMovies(): List<MovieDto>

    @GET("/api/banners")
    suspend fun getBanners(): List<MovieDto>
}