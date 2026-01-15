package com.artur.cinematicketbooking.presentation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.artur.cinematicketbooking.R
import com.artur.cinematicketbooking.data.api.RetrofitInstance
import com.artur.cinematicketbooking.data.repository.UserRepositoryImpl
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.Base64

class IntroFragment : Fragment() {

    private val userRepository: UserRepositoryImpl by lazy {
        UserRepositoryImpl(RetrofitInstance.userApi)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.activity_intro, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE)
        val token = prefs.getString("access_token", null)

        if (token != null && isTokenValid(token)) {
            RetrofitInstance.updateTokenProvider { token }

            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    userRepository.getUserProfile()
                    findNavController().navigate(R.id.explorerFragment)
                } catch (e: retrofit2.HttpException) {
                    if (e.code() == 403 || e.code() == 404) {
                        invalidateSession(prefs)
                        Toast.makeText(
                            requireContext(),
                            "Your session is no longer valid. Please log in again.",
                            Toast.LENGTH_LONG
                        ).show()
                        findNavController().navigate(R.id.authFragment)
                    } else {
                        throw e
                    }
                } catch (e: Exception) {
                    invalidateSession(prefs)
                    Toast.makeText(
                        requireContext(),
                        "Unexpected error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigate(R.id.authFragment)
                }
            }
        } else {
            val buttonGetStarted = view.findViewById<AppCompatButton>(R.id.buttonGetStarted)
            buttonGetStarted.setOnClickListener {
                findNavController().navigate(R.id.authFragment)
            }
        }
    }

    private fun isTokenValid(token: String): Boolean {
        return try {
            val parts = token.split(".")
            if (parts.size != 3) return false
            val payload = String(Base64.getUrlDecoder().decode(parts[1]))
            val expiry = JSONObject(payload).getLong("exp")
            val now = System.currentTimeMillis() / 1000
            expiry > now
        } catch (e: Exception) {
            false
        }
    }

    private fun invalidateSession(prefs: SharedPreferences) {
        prefs.edit().remove("access_token").apply()
        RetrofitInstance.updateTokenProvider { null }
    }
}

