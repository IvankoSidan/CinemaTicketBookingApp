package com.ivan.cinematicketbooking.features_home.data.api

import com.ivan.cinematicketbooking.features_home.data.model.BannerDto
import retrofit2.http.GET


interface BannerApi {
    @GET("/api/banners")
    suspend fun getBanners(): List<BannerDto>
}
