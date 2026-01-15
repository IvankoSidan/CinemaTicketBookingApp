package com.artur.cinematicketbooking.domain.usecase

import com.artur.cinematicketbooking.domain.model.Banner
import com.artur.cinematicketbooking.domain.repository.BannerRepository

class GetBannersUseCase(private val repository: BannerRepository) {
    suspend operator fun invoke(): List<Banner> = repository.getBanners()
}