package com.ivan.cinematicketbooking.features_booking.presentation.viewmodel

import com.ivan.cinematicketbooking.core.base.BaseViewModel
import com.ivan.cinematicketbooking.core.base.NavigationEvent
import com.ivan.cinematicketbooking.features_booking.data.model.BookingState
import com.ivan.cinematicketbooking.features_booking.domain.model.Booking
import com.ivan.cinematicketbooking.features_booking.domain.model.Seat
import com.ivan.cinematicketbooking.features_booking.domain.model.SeatStatus
import com.ivan.cinematicketbooking.features_booking.domain.usecase.CancelBookingUseCase
import com.ivan.cinematicketbooking.features_booking.domain.usecase.CreateBookingUseCase
import com.ivan.cinematicketbooking.features_booking.domain.usecase.GetSeatStatusesUseCase
import com.ivan.cinematicketbooking.features_booking.domain.usecase.GetUserBookingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class BookingViewModel @Inject constructor(
    private val createBookingUseCase: CreateBookingUseCase,
    private val getUserBookingsUseCase: GetUserBookingsUseCase,
    private val cancelBookingUseCase: CancelBookingUseCase,
    private val getSeatStatusesUseCase: GetSeatStatusesUseCase
) : BaseViewModel() {

    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> = _bookings

    private val _seats = MutableStateFlow<List<Seat>>(emptyList())
    val seats: StateFlow<List<Seat>> = _seats

    private val _availableDates = MutableStateFlow<List<LocalDate>>(emptyList())
    val availableDates: StateFlow<List<LocalDate>> = _availableDates

    private val _availableTimes = MutableStateFlow<List<String>>(emptyList())
    val availableTimes: StateFlow<List<String>> = _availableTimes

    private val _bookingState = MutableStateFlow<BookingState>(BookingState.Idle)
    val bookingState: StateFlow<BookingState> = _bookingState

    private val _selectedSeatInfo = MutableStateFlow(Pair(0, 0.0))
    val selectedSeatInfo: StateFlow<Pair<Int, Double>> = _selectedSeatInfo

    private var selectedDate: LocalDate = LocalDate.now()
    private var selectedTime: String? = null

    fun loadAvailableDates() {
        val dates = List(7) { index -> LocalDate.now().plusDays(index.toLong()) }
        _availableDates.value = dates
        this.selectedDate = dates.firstOrNull() ?: LocalDate.now()
    }

    fun onDateSelected(date: LocalDate) {
        this.selectedDate = date
        _availableTimes.value = listOf("10:00", "13:30", "17:00", "20:30", "23:00")
    }

    fun onTimeSelected(time: String, movieId: Long) {
        this.selectedTime = time
        launch {
            try {
                val showTime = LocalDateTime.of(selectedDate, LocalTime.parse(time))
                    .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                _seats.value = getSeatStatusesUseCase(movieId, showTime)
                updateSelectedSeatInfo()
            } catch (e: Exception) {
                _seats.value = emptyList()
            }
        }
    }

    fun toggleSeatSelection(seatNumber: String) {
        _seats.value = _seats.value.map {
            if (it.number == seatNumber) {
                when (it.status) {
                    SeatStatus.AVAILABLE -> it.copy(status = SeatStatus.SELECTED)
                    SeatStatus.SELECTED -> it.copy(status = SeatStatus.AVAILABLE)
                    else -> it
                }
            } else it
        }
        updateSelectedSeatInfo()
    }

    private fun updateSelectedSeatInfo() {
        val selected = _seats.value.filter { it.status == SeatStatus.SELECTED }
        _selectedSeatInfo.value = Pair(selected.size, selected.sumOf { it.price })
    }

    fun createBookingForSelectedSeats(movieId: Long) {
        val time = selectedTime ?: return
        val selectedSeats = _seats.value.filter { it.status == SeatStatus.SELECTED }
        if (selectedSeats.isEmpty()) return

        launch {
            _bookingState.value = BookingState.Loading
            try {
                val showTime = LocalDateTime.of(selectedDate, LocalTime.parse(time))
                selectedSeats.forEach { seat ->
                    createBookingUseCase(movieId, seat.number, seat.price, showTime)
                }
                _bookingState.value = BookingState.Success
            } catch (e: Exception) {
                _bookingState.value = BookingState.Error(e.message ?: "Booking failed")
            }
        }
    }

    fun goBack() = navigate(NavigationEvent.Back)
}