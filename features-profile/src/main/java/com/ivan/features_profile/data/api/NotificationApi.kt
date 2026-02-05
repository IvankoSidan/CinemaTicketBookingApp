package com.ivan.features_profile.data.api

import com.ivan.features_profile.data.model.NotificationDto
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface NotificationApi {
    @GET("/api/notifications/my")
    suspend fun getUserNotifications(): List<NotificationDto>

    @GET("/api/notifications/my/unread")
    suspend fun getUnreadNotifications(): List<NotificationDto>

    @PATCH("/api/notifications/{id}/read")
    suspend fun markAsRead(@Path("id") id: Long): NotificationDto
}
