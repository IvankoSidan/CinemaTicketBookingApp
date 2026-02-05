package com.ivan.cinematicketbooking.features_booking.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ivan.cinematicketbooking.features_booking.R

class TimeAdapter(
    private val onTimeSelected: (String) -> Unit
) : ListAdapter<String, TimeAdapter.TimeViewHolder>(TimeDiffCallback()) {

    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_time, parent, false)
        return TimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.bind(getItem(position), position == selectedPosition)
    }

    fun setSelectedTime(time: String) {
        val position = currentList.indexOf(time)
        if (position != -1 && position != selectedPosition) {
            val previous = selectedPosition
            selectedPosition = position
            notifyItemChanged(previous)
            notifyItemChanged(selectedPosition)
        }
    }

    inner class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val timeText: TextView = itemView.findViewById(R.id.TextViewTime)

        fun bind(time: String, isSelected: Boolean) {
            timeText.text = time
            timeText.setBackgroundResource(
                if (isSelected) R.drawable.selected_date_bg else R.drawable.light_black_bg
            )

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION && position != selectedPosition) {
                    val previous = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(previous)
                    notifyItemChanged(selectedPosition)
                    onTimeSelected(time)
                }
            }
        }
    }

    class TimeDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }
}
