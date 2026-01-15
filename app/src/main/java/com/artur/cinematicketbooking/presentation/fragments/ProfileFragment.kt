package com.artur.cinematicketbooking.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.artur.cinematicketbooking.R
import com.artur.cinematicketbooking.presentation.ui.MainActivity
import com.artur.cinematicketbooking.presentation.viewmodel.NotificationViewModel
import com.artur.cinematicketbooking.presentation.viewmodel.ProfileViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by activityViewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }

    private val notificationViewModel: NotificationViewModel by activityViewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }

    private lateinit var btnBack: ImageView
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var btnLogout: AppCompatButton
    private lateinit var btnNotifications: View
    private lateinit var notificationBadge: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnBack = view.findViewById(R.id.btn_back_profile)
        tvUsername = view.findViewById(R.id.text_user_name)
        tvEmail = view.findViewById(R.id.text_user_email)
        btnLogout = view.findViewById(R.id.button_logout)
        btnNotifications = view.findViewById(R.id.button_notifications)
        notificationBadge = view.findViewById(R.id.notification_badge)

        setupListeners()
        observeProfile()
        observeNotifications()
        profileViewModel.loadProfile()
        notificationViewModel.loadUnreadCount()
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        btnLogout.setOnClickListener {
            profileViewModel.logout()
            findNavController().navigate(R.id.authFragment)
        }

        btnNotifications.setOnClickListener {
            findNavController().navigate(R.id.fragmentNotifications)
        }
    }

    private fun observeProfile() {
        lifecycleScope.launch {
            profileViewModel.user.collectLatest { user ->
                user?.let {
                    tvUsername.text = it.username
                    tvEmail.text = it.email
                }
            }
        }
    }

    private fun observeNotifications() {
        lifecycleScope.launch {
            notificationViewModel.unreadCount.collectLatest { unreadCount ->
                notificationBadge.visibility = if (unreadCount > 0) View.VISIBLE else View.GONE
                notificationBadge.text = unreadCount.toString()
            }
        }
    }
}