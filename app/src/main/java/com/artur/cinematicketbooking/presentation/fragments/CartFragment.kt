package com.artur.cinematicketbooking.presentation.fragments


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artur.cinematicketbooking.R
import com.artur.cinematicketbooking.domain.model.Seat
import com.artur.cinematicketbooking.domain.model.SeatStatus
import com.artur.cinematicketbooking.presentation.adapters.DateAdapter
import com.artur.cinematicketbooking.presentation.adapters.SeatAdapter
import com.artur.cinematicketbooking.presentation.adapters.TimeAdapter
import com.artur.cinematicketbooking.presentation.ui.MainActivity
import com.artur.cinematicketbooking.presentation.viewmodel.BookingState
import com.artur.cinematicketbooking.presentation.viewmodel.BookingViewModel
import com.artur.cinematicketbooking.presentation.viewmodel.MovieViewModel
import com.artur.cinematicketbooking.presentation.viewmodel.NotificationViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate


class CartFragment : Fragment() {

    private val movieViewModel: MovieViewModel by activityViewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }
    private val bookingViewModel: BookingViewModel by activityViewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }
    private val notificationViewModel: NotificationViewModel by activityViewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }

    private lateinit var rvDateList: RecyclerView
    private lateinit var rvTimeList: RecyclerView
    private lateinit var rvSeatList: RecyclerView
    private lateinit var tvTotalPrice: TextView
    private lateinit var tvSelectedCount: TextView
    private lateinit var btnBack: ImageView
    private lateinit var btnDownloadTicket: AppCompatButton

    private lateinit var dateAdapter: DateAdapter
    private lateinit var timeAdapter: TimeAdapter
    private lateinit var seatAdapter: SeatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_cart, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setupAdapters()
        setupListeners()
        setupObservers()
        bookingViewModel.loadAvailableDates()
    }

    private fun bindViews(view: View) {
        rvDateList = view.findViewById(R.id.rv_date_list)
        rvTimeList = view.findViewById(R.id.rv_time_list)
        rvSeatList = view.findViewById(R.id.rv_seat_list)
        tvTotalPrice = view.findViewById(R.id.tv_total_price)
        tvSelectedCount = view.findViewById(R.id.tv_selected_count)
        btnBack = view.findViewById(R.id.btn_back)
        btnDownloadTicket = view.findViewById(R.id.btn_download_ticket)
    }

    private fun setupAdapters() {
        dateAdapter = DateAdapter { date ->
            bookingViewModel.onDateSelected(date)
        }
        rvDateList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvDateList.adapter = dateAdapter

        timeAdapter = TimeAdapter { time ->
            movieViewModel.selectedMovieId.value?.let { movieId ->
                bookingViewModel.onTimeSelected(time, movieId)
            }
        }
        rvTimeList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvTimeList.adapter = timeAdapter

        seatAdapter = SeatAdapter { seat -> handleSeatSelection(seat) }
        rvSeatList.layoutManager = GridLayoutManager(requireContext(), 7)
        rvSeatList.adapter = seatAdapter
    }

    private fun setupListeners() {
        btnBack.setOnClickListener { findNavController().popBackStack() }
        btnDownloadTicket.setOnClickListener { confirmBooking() }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            bookingViewModel.availableDates.collectLatest { dates ->
                dateAdapter.submitList(dates)
                if (dates.isNotEmpty()) {
                    dateAdapter.setSelectedDate(dates.first())
                    bookingViewModel.onDateSelected(dates.first())
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            bookingViewModel.availableTimes.collectLatest { times ->
                timeAdapter.submitList(times)
                timeAdapter.setSelectedTime("")
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            bookingViewModel.seats.collectLatest { seats ->
                seatAdapter.submitList(seats)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            bookingViewModel.selectedSeatInfo.collectLatest { (count, totalPrice) ->
                tvTotalPrice.text = "$${totalPrice.toInt()}"
                tvSelectedCount.text = "$count ${if (count == 1) "Seat" else "Seats"} Selected"
                btnDownloadTicket.isEnabled = count > 0
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            bookingViewModel.bookingState.collectLatest { state ->
                when (state) {
                    is BookingState.Loading -> {
                        btnDownloadTicket.isEnabled = false
                        btnDownloadTicket.text = "Processing..."
                    }
                    is BookingState.Success -> {
                        btnDownloadTicket.isEnabled = true
                        btnDownloadTicket.text = "Download Ticket"
                        notificationViewModel.loadUnreadCount()
                        showBookingSuccess()
                    }
                    is BookingState.Error -> {
                        btnDownloadTicket.isEnabled = true
                        btnDownloadTicket.text = "Download Ticket"
                        showError(state.message)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun handleSeatSelection(seat: Seat) {
        when (seat.status) {
            SeatStatus.AVAILABLE -> bookingViewModel.selectSeat(seat.number)
            SeatStatus.SELECTED -> bookingViewModel.deselectSeat(seat.number)
            else -> return
        }
    }

    private fun confirmBooking() {
        val movieId = movieViewModel.selectedMovieId.value ?: return showError("Movie not selected")
        bookingViewModel.createBookingForSelectedSeats(movieId)
    }

    private fun showBookingSuccess() {
        AlertDialog.Builder(requireContext())
            .setTitle("Booking confirmed!")
            .setMessage("Your booking was successful. A notification has been sent to your account.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                findNavController().popBackStack()
            }
            .setCancelable(false)
            .show()
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}