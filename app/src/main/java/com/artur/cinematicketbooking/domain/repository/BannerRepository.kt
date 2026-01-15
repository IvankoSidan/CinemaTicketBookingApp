package com.artur.cinematicketbooking.domain.repository

import com.artur.cinematicketbooking.domain.model.Banner

interface BannerRepository {
    suspend fun getBanners(): List<Banner>
}