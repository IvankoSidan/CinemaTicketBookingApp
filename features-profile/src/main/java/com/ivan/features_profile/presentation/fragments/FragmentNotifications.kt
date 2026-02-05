package com.ivan.features_profile.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ivan.cinematicketbooking.core.base.BaseFragment
import com.ivan.cinematicketbooking.features.profile.databinding.FragmentNotificationsBinding
import com.ivan.core.di.ViewModelFactoryProvider
import com.ivan.features_profile.presentation.adapters.NotificationAdapter
import com.ivan.features_profile.presentation.viewmodel.NotificationViewModel
import kotlinx.coroutines.launch

class FragmentNotifications : BaseFragment<FragmentNotificationsBinding>(FragmentNotificationsBinding::inflate) {

    private val viewModel: NotificationViewModel by activityViewModels {
        (requireActivity().application as ViewModelFactoryProvider).provideViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notificationAdapter = NotificationAdapter { notification ->
            if (!notification.isRead) viewModel.markAsRead(notification.id)
        }

        binding.recyclerNotifications.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = notificationAdapter
        }

        binding.btnBack.setOnClickListener { viewModel.goBack() }
        binding.btnAll.setOnClickListener { viewModel.loadAll() }
        binding.btnUnread.setOnClickListener { viewModel.loadUnread() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.notifications.collect { list ->
                        notificationAdapter.submitList(list)
                        binding.emptyState.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
                    }
                }
                launch {
                    viewModel.navigationEvent.collect {
                        findNavController().popBackStack()
                    }
                }
            }
        }

        viewModel.loadAll()
    }
}