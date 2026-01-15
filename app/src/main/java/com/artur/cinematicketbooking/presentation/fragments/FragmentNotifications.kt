package com.artur.cinematicketbooking.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.artur.cinematicketbooking.databinding.FragmentNotificationsBinding
import com.artur.cinematicketbooking.presentation.adapters.NotificationAdapter
import com.artur.cinematicketbooking.presentation.ui.MainActivity
import com.artur.cinematicketbooking.presentation.viewmodel.NotificationViewModel
import kotlinx.coroutines.launch

class FragmentNotifications : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private val notificationViewModel: NotificationViewModel by activityViewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }

    private lateinit var notificationAdapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
        loadNotifications()
    }

    private fun setupRecyclerView() {
        notificationAdapter = NotificationAdapter { notification ->
            if (!notification.isRead) {
                notificationViewModel.markAsRead(notification.id)
            }
        }

        binding.recyclerNotifications.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = notificationAdapter
        }
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnAll.setOnClickListener {
            setActiveFilter(binding.btnAll, binding.btnUnread)
            notificationViewModel.loadAll()
        }

        binding.btnUnread.setOnClickListener {
            setActiveFilter(binding.btnUnread, binding.btnAll)
            notificationViewModel.loadUnread()
        }
    }

    private fun setActiveFilter(active: android.widget.TextView, inactive: android.widget.TextView) {
        active.setTextColor(requireContext().getColor(com.artur.cinematicketbooking.R.color.orange))
        active.setTypeface(active.typeface, android.graphics.Typeface.BOLD)
        inactive.setTextColor(requireContext().getColor(com.artur.cinematicketbooking.R.color.grey))
        inactive.setTypeface(inactive.typeface, android.graphics.Typeface.NORMAL)
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                notificationViewModel.notifications.collect { notifications ->
                    notificationAdapter.submitList(notifications)
                    if (notifications.isEmpty()) {
                        binding.emptyState.visibility = View.VISIBLE
                        binding.recyclerNotifications.visibility = View.GONE
                    } else {
                        binding.emptyState.visibility = View.GONE
                        binding.recyclerNotifications.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun loadNotifications() {
        notificationViewModel.loadAll()
        setActiveFilter(binding.btnAll, binding.btnUnread)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}