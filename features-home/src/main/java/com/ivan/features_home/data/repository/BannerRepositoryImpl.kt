package com.ivan.cinematicketbooking.features_home.data.repository

import com.ivan.cinematicketbooking.features_home.data.api.BannerApi
import com.ivan.cinematicketbooking.features_home.data.mapper.toDomain
import com.ivan.cinematicketbooking.features_home.domain.model.Banner
import com.ivan.cinematicketbooking.features_home.domain.repository.BannerRepository
import javax.inject.Inject

class BannerRepositoryImpl @Inject constructor(
    private val api: BannerApi
) : BannerRepository {
    override suspend fun getBanners(): List<Banner> {
        return api.getBanners().map { it.toDomain() }
    }
}