package com.ivan.cinematicketbooking.di

import android.content.Context
import com.ivan.core.util.MutableTokenProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenProviderImpl @Inject constructor(
    context: Context
) : MutableTokenProvider {

    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    private var cachedToken: String? = null

    companion object {
        private const val KEY_TOKEN = "access_token"
    }

    init {
        cachedToken = prefs.getString(KEY_TOKEN, null)
    }

    override fun getToken(): String? {
        return cachedToken
    }

    override fun updateToken(token: String?) {
        cachedToken = token
        prefs.edit().apply {
            if (token == null) {
                remove(KEY_TOKEN)
            } else {
                putString(KEY_TOKEN, token)
            }
            apply()
        }
    }
}