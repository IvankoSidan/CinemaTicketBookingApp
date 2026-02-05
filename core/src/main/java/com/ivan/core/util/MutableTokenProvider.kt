package com.ivan.core.util

interface MutableTokenProvider : TokenProvider {
    fun updateToken(token: String?)
}