package com.ivan.cinematicketbooking.features_booking.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ivan.cinematicketbooking.features_booking.R
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class DateAdapter(
    private val onDateSelected: (LocalDate) -> Unit
) : ListAdapter<LocalDate, DateAdapter.DateViewHolder>(DateDiffCallback()) {

    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)
        return DateViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.bind(getItem(position), position == selectedPosition)
    }

    fun setSelectedDate(date: LocalDate) {
        val position = currentList.indexOf(date)
        if (position != -1 && position != selectedPosition) {
            val previous = selectedPosition
            selectedPosition = position
            notifyItemChanged(previous)
            notifyItemChanged(selectedPosition)
        }
    }

    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateText: TextView = itemView.findViewById(R.id.dateTxt)
        private val monthText: TextView = itemView.findViewById(R.id.datMonthTxt)
        private val container: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)

        fun bind(date: LocalDate, isSelected: Boolean) {
            dateText.text = date.dayOfMonth.toString()
            monthText.text = date.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())
            container.setBackgroundResource(
                if (isSelected) R.drawable.selected_date_bg else R.drawable.light_black_bg
            )

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION && position != selectedPosition) {
                    val previous = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(previous)
                    notifyItemChanged(selectedPosition)
                    onDateSelected(date)
                }
            }
        }
    }

    class DateDiffCallback : DiffUtil.ItemCallback<LocalDate>() {
        override fun areItemsTheSame(oldItem: LocalDate, newItem: LocalDate) = oldItem == newItem
        override fun areContentsTheSame(oldItem: LocalDate, newItem: LocalDate) = oldItem == newItem
    }
}

