package com.ivan.features_profile.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ivan.cinematicketbooking.core.base.BaseFragment
import com.ivan.cinematicketbooking.core.base.NavigationEvent
import com.ivan.cinematicketbooking.features.profile.databinding.FragmentProfileBinding
import com.ivan.cinematicketbooking.nagivation.NavigatorProvider
import com.ivan.core.di.ViewModelFactoryProvider
import com.ivan.features_profile.presentation.viewmodel.NotificationViewModel
import com.ivan.features_profile.presentation.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val navigator by lazy {
        (requireActivity().application as NavigatorProvider).provideNavigator()
    }

    private val profileViewModel: ProfileViewModel by activityViewModels {
        (requireActivity().application as ViewModelFactoryProvider).provideViewModelFactory()
    }

    private val notificationViewModel: NotificationViewModel by activityViewModels {
        (requireActivity().application as ViewModelFactoryProvider).provideViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackProfile.setOnClickListener { navigator.goBack() }
        binding.buttonLogout.setOnClickListener { profileViewModel.logout() }
        binding.buttonNotifications.setOnClickListener {
            navigator.navigateToNotifications()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    profileViewModel.user.collect { user ->
                        user?.let {
                            binding.textUserName.text = it.username
                            binding.textUserEmail.text = it.email
                        }
                    }
                }
                launch {
                    notificationViewModel.unreadCount.collect { count ->
                        binding.notificationBadge.visibility = if (count > 0) View.VISIBLE else View.GONE
                        binding.notificationBadge.text = count.toString()
                    }
                }
                launch {
                    profileViewModel.navigationEvent.collect { event ->
                        when (event) {
                            is NavigationEvent.ToAuth -> navigator.navigateToAuth()
                            is NavigationEvent.Back -> navigator.goBack()
                            else -> Unit
                        }
                    }
                }
            }
        }

        profileViewModel.loadProfile()
        notificationViewModel.loadUnreadCount()
    }
}