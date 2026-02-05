package com.ivan.cinematicketbooking.features_home.domain.usecase

import com.ivan.cinematicketbooking.features_home.domain.model.Banner
import com.ivan.cinematicketbooking.features_home.domain.repository.BannerRepository
import javax.inject.Inject


class GetBannersUseCase @Inject constructor(private val repository: BannerRepository) {
    suspend operator fun invoke(): List<Banner> = repository.getBanners()
}