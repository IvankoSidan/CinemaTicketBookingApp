package com.ivan.features_home.di

import androidx.lifecycle.ViewModel
import com.ivan.cinematicketbooking.features_home.data.api.BannerApi
import com.ivan.cinematicketbooking.features_home.data.api.MovieApi
import com.ivan.cinematicketbooking.features_home.data.repository.BannerRepositoryImpl
import com.ivan.cinematicketbooking.features_home.data.repository.MovieRepositoryImpl
import com.ivan.cinematicketbooking.features_home.domain.repository.BannerRepository
import com.ivan.cinematicketbooking.features_home.domain.repository.MovieRepository
import com.ivan.cinematicketbooking.presentation.viewmodel.MovieViewModel
import com.ivan.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module
abstract class HomeModule {

    @Binds
    abstract fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun bindBannerRepository(impl: BannerRepositoryImpl): BannerRepository

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    abstract fun bindMovieViewModel(viewModel: MovieViewModel): ViewModel

    companion object {
        @Provides
        fun provideMovieApi(retrofit: Retrofit): MovieApi {
            return retrofit.create(MovieApi::class.java)
        }

        @Provides
        fun provideBannerApi(retrofit: Retrofit): BannerApi {
            return retrofit.create(BannerApi::class.java)
        }
    }
}