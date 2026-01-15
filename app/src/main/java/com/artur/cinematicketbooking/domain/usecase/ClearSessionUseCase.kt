package com.artur.cinematicketbooking.domain.usecase

import android.content.Context
import androidx.core.content.edit

class ClearSessionUseCase(private val context: Context) {

    fun invoke() {
        val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        prefs.edit {
            clear()
            apply()
        }
    }
}
