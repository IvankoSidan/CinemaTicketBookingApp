package com.artur.cinematicketbooking.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artur.cinematicketbooking.domain.model.Booking
import com.artur.cinematicketbooking.domain.model.Seat
import com.artur.cinematicketbooking.domain.model.SeatStatus
import com.artur.cinematicketbooking.domain.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class BookingViewModel(
    private val createBookingUseCase: CreateBookingUseCase,
    private val getUserBookingsUseCase: GetUserBookingsUseCase,
    private val cancelBookingUseCase: CancelBookingUseCase,
    private val getSeatStatusesUseCase: GetSeatStatusesUseCase
) : ViewModel() {

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
        loadAvailableTimes()
    }

    fun onTimeSelected(time: String, movieId: Long) {
        this.selectedTime = time
        loadSeats(movieId, this.selectedDate, time)
    }

    private fun loadAvailableTimes() {
        _availableTimes.value = listOf("10:00", "13:30", "17:00", "20:30", "23:00")
        this.selectedTime = null
        this._seats.value = emptyList()
        updateSelectedSeatInfo()
    }

    fun loadSeats(movieId: Long, date: LocalDate, time: String) {
        viewModelScope.launch {
            try {
                val localDateTime = LocalDateTime.of(date, LocalTime.parse(time))
                val showTime = localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                _seats.value = getSeatStatusesUseCase(movieId, showTime)
                updateSelectedSeatInfo()
            } catch (e: Exception) {
                _seats.value = emptyList()
                updateSelectedSeatInfo()
            }
        }
    }

    fun selectSeat(seatNumber: String) {
        _seats.value = _seats.value.map {
            if (it.number == seatNumber && it.status == SeatStatus.AVAILABLE) it.copy(status = SeatStatus.SELECTED)
            else it
        }
        updateSelectedSeatInfo()
    }

    fun deselectSeat(seatNumber: String) {
        _seats.value = _seats.value.map {
            if (it.number == seatNumber && it.status == SeatStatus.SELECTED) it.copy(status = SeatStatus.AVAILABLE)
            else it
        }
        updateSelectedSeatInfo()
    }

    private fun updateSelectedSeatInfo() {
        val selected = _seats.value.filter { it.status == SeatStatus.SELECTED }
        _selectedSeatInfo.value = Pair(selected.size, selected.sumOf { it.price })
    }

    fun createBookingForSelectedSeats(movieId: Long) {
        viewModelScope.launch {
            val time = selectedTime
            if (time == null) {
                _bookingState.value = BookingState.Error("Please select a show time.")
                return@launch
            }

            val selectedSeats = _seats.value.filter { it.status == SeatStatus.SELECTED }
            if (selectedSeats.isEmpty()) {
                _bookingState.value = BookingState.Error("Please select at least one seat.")
                return@launch
            }

            val showTime = LocalDateTime.of(selectedDate, LocalTime.parse(time))

            _bookingState.value = BookingState.Loading
            try {
                selectedSeats.forEach { seat ->
                    createBookingUseCase(movieId, seat.number, seat.price, showTime)
                }
                _bookings.value = getUserBookingsUseCase()
                _bookingState.value = BookingState.Success
            } catch (e: Exception) {
                _bookingState.value = BookingState.Error(e.message ?: "Booking failed")
            }
        }
    }

    fun loadBookings() = viewModelScope.launch {
        _bookings.value = getUserBookingsUseCase()
    }

    fun cancelBooking(id: Long) = viewModelScope.launch {
        cancelBookingUseCase(id)
        _bookings.value = getUserBookingsUseCase()
    }
}

sealed class BookingState {
    object Idle : BookingState()
    object Loading : BookingState()
    object Success : BookingState()
    data class Error(val message: String) : BookingState()
}