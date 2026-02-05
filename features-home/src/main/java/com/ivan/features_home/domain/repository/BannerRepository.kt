package com.ivan.cinematicketbooking.features_home.domain.repository

import com.ivan.cinematicketbooking.features_home.domain.model.Banner

interface BannerRepository {
    suspend fun getBanners(): List<Banner>
}