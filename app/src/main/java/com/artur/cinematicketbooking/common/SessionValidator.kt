package com.artur.cinematicketbooking.common

import android.content.Context
import com.artur.cinematicketbooking.data.api.RetrofitInstance

object SessionValidator {
    fun invalidate(context: Context) {
        context.getSharedPreferences("auth", Context.MODE_PRIVATE)
            .edit().remove("access_token").apply()

        RetrofitInstance.updateTokenProvider { null }
    }
}
