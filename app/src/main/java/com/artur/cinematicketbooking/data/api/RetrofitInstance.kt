package com.artur.cinematicketbooking.data.api

import android.content.Context
import com.artur.cinematicketbooking.common.AuthInterceptor
import com.artur.cinematicketbooking.presentation.adapters.LocalDateTimeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

object RetrofitInstance {

    private const val BASE_URL = "https://10.0.2.2:8443/api/"

    private var tokenProvider: () -> String? = { null }

    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .create()

    fun init(provider: () -> String?) {
        tokenProvider = provider
    }

    fun updateTokenProvider(provider: () -> String?) {
        tokenProvider = provider
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor { tokenProvider() })
            .build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val authApi: AuthApi by lazy { retrofit.create(AuthApi::class.java) }
    val movieApi: MovieApi by lazy { retrofit.create(MovieApi::class.java) }
    val bookingApi: BookingApi by lazy { retrofit.create(BookingApi::class.java) }
    val notificationApi: NotificationApi by lazy { retrofit.create(NotificationApi::class.java) }
    val userApi: UserApi by lazy { retrofit.create(UserApi::class.java) }
    val bannerApi: BannerApi by lazy { retrofit.create(BannerApi::class.java) }
}
