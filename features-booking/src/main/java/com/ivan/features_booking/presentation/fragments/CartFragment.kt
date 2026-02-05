package com.ivan.cinematicketbooking.features_booking.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ivan.cinematicketbooking.core.base.BaseFragment
import com.ivan.cinematicketbooking.features_booking.databinding.FragmentCartBinding
import com.ivan.cinematicketbooking.features_booking.data.model.BookingState
import com.ivan.cinematicketbooking.features_booking.presentation.adapters.DateAdapter
import com.ivan.cinematicketbooking.features_booking.presentation.adapters.SeatAdapter
import com.ivan.cinematicketbooking.features_booking.presentation.adapters.TimeAdapter
import com.ivan.cinematicketbooking.features_booking.presentation.viewmodel.BookingViewModel
import com.ivan.cinematicketbooking.presentation.viewmodel.MovieViewModel
import com.ivan.core.di.ViewModelFactoryProvider
import kotlinx.coroutines.launch

class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {

    private val bookingViewModel: BookingViewModel by activityViewModels {
        (requireActivity().application as ViewModelFactoryProvider).provideViewModelFactory()
    }
    private val movieViewModel: MovieViewModel by activityViewModels {
        (requireActivity().application as ViewModelFactoryProvider).provideViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapters()
        setupListeners()
        observeViewModel()

        bookingViewModel.loadAvailableDates()
    }

    private fun setupAdapters() {
        val dateAdapter = DateAdapter { bookingViewModel.onDateSelected(it) }
        val timeAdapter = TimeAdapter { time ->
            movieViewModel.selectedMovie.value?.id?.let { bookingViewModel.onTimeSelected(time, it) }
        }
        val seatAdapter = SeatAdapter { bookingViewModel.toggleSeatSelection(it.number) }

        binding.rvDateList.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = dateAdapter
        }
        binding.rvTimeList.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = timeAdapter
        }
        binding.rvSeatList.apply {
            layoutManager = GridLayoutManager(requireContext(), 7)
            adapter = seatAdapter
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener { bookingViewModel.goBack() }
        binding.btnDownloadTicket.setOnClickListener {
            movieViewModel.selectedMovie.value?.id?.let {
                bookingViewModel.createBookingForSelectedSeats(it)
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { bookingViewModel.availableDates.collect { (binding.rvDateList.adapter as? DateAdapter)?.submitList(it) } }
                launch { bookingViewModel.availableTimes.collect { (binding.rvTimeList.adapter as? TimeAdapter)?.submitList(it) } }
                launch { bookingViewModel.seats.collect { (binding.rvSeatList.adapter as? SeatAdapter)?.submitList(it) } }
                launch {
                    bookingViewModel.selectedSeatInfo.collect { (count, price) ->
                        binding.tvTotalPrice.text = "$${price}"
                        binding.tvSelectedCount.text = "$count Seats Selected"
                        binding.btnDownloadTicket.isEnabled = count > 0
                    }
                }
                launch {
                    bookingViewModel.bookingState.collect { state ->
                        when (state) {
                            is BookingState.Success -> {
                                Toast.makeText(requireContext(), "Booking Successful!", Toast.LENGTH_SHORT).show()
                                findNavController().popBackStack()
                            }
                            is BookingState.Error -> Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                            else -> Unit
                        }
                    }
                }
                launch {
                    bookingViewModel.navigationEvent.collect {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }
}