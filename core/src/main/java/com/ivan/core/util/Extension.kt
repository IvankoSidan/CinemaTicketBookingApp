package com.ivan.cinematicketbooking.core.util

import android.content.res.Resources
import android.view.View

fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}