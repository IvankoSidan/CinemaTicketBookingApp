package com.artur.cinematicketbooking.data.repository

import android.security.keystore.UserNotAuthenticatedException
import com.artur.cinematicketbooking.common.toDomain
import com.artur.cinematicketbooking.data.api.UserApi
import com.artur.cinematicketbooking.domain.model.User
import com.artur.cinematicketbooking.domain.repository.UserRepository
import retrofit2.HttpException

class UserRepositoryImpl(private val api: UserApi) : UserRepository {
    override suspend fun getUserProfile(): User {
        return try {
            api.getUserProfile().toDomain()
        } catch (e: HttpException) {
            if (e.code() == 403 || e.code() == 404) {
                throw UserNotFoundException("The user was not found or the token is invalid !")
            } else {
                throw e
            }
        }
    }
}

class UserNotFoundException(message: String) : Exception(message)
