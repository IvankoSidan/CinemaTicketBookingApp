package com.artur.cinematicketbooking.data.repository

import com.artur.cinematicketbooking.common.toDomain
import com.artur.cinematicketbooking.data.api.BannerApi
import com.artur.cinematicketbooking.data.api.MovieApi
import com.artur.cinematicketbooking.domain.model.Banner
import com.artur.cinematicketbooking.domain.repository.BannerRepository

class BannerRepositoryImpl(
    private val api: BannerApi
) : BannerRepository {
    override suspend fun getBanners(): List<Banner> {
        return api.getBanners().map { it.toDomain() }
    }
}