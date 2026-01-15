package com.artur.cinematicketbooking.data.api

import com.artur.cinematicketbooking.data.model.BannerDto
import retrofit2.http.GET

interface BannerApi {
    @GET("/api/banners")
    suspend fun getBanners(): List<BannerDto>
}
