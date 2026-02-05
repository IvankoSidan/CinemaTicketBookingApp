package com.ivan.cinematicketbooking.features_booking.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ivan.cinematicketbooking.features_booking.R
import com.ivan.cinematicketbooking.features_booking.domain.model.Seat
import com.ivan.cinematicketbooking.features_booking.domain.model.SeatStatus

class SeatAdapter(
    private val onSeatSelected: (Seat) -> Unit
) : ListAdapter<Seat, SeatAdapter.SeatViewHolder>(
    SeatDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_seat, parent, false)
        return SeatViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SeatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val seatView: TextView = itemView.findViewById(R.id.seat)

        fun bind(seat: Seat) {
            seatView.text = seat.number
            val backgroundRes = when (seat.status) {
                SeatStatus.AVAILABLE -> R.drawable.ic_seat_available
                SeatStatus.SELECTED -> R.drawable.ic_seat_selected
                SeatStatus.UNAVAILABLE -> R.drawable.ic_seat_unavialable
            }
            seatView.setBackgroundResource(backgroundRes)

            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION &&
                (seat.status == SeatStatus.AVAILABLE || seat.status == SeatStatus.SELECTED)
            ) {
                seatView.isClickable = true
                seatView.setOnClickListener { onSeatSelected(seat) }
            } else {
                seatView.isClickable = false
                seatView.setOnClickListener(null)
            }
        }
    }

    class SeatDiffCallback : DiffUtil.ItemCallback<Seat>() {
        override fun areItemsTheSame(oldItem: Seat, newItem: Seat): Boolean {
            return oldItem.number == newItem.number && oldItem.row == newItem.row
        }

        override fun areContentsTheSame(oldItem: Seat, newItem: Seat): Boolean {
            return oldItem == newItem
        }
    }
}
