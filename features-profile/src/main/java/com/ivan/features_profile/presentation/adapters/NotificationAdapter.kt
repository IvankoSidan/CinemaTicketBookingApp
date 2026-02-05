package com.ivan.features_profile.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ivan.cinematicketbooking.features.profile.R
import com.ivan.cinematicketbooking.features.profile.databinding.ItemNotificationBinding
import com.ivan.features_profile.domain.model.Notification
import java.time.Duration
import java.time.LocalDateTime

class NotificationAdapter(
    private val onNotificationClick: (Notification) -> Unit
) : ListAdapter<Notification, NotificationAdapter.NotificationViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = getItem(position)
        holder.bind(notification)
        holder.itemView.setOnClickListener { onNotificationClick(notification) }
    }

    class NotificationViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notification: Notification) {
            binding.notificationTitle.text = notification.title
            binding.notificationMessage.text = notification.message
            binding.notificationTime.text = getTimeAgo(notification.createdAt)
            
            binding.unreadIndicator.visibility = if (notification.isRead) View.GONE else View.VISIBLE
            
            val iconRes = when (notification.type) {
                "booking" -> R.drawable.ic_ticket
                "system" -> R.drawable.ic_system
                "promo" -> R.drawable.ic_promo
                else -> R.drawable.ic_notification_info
            }
            binding.notificationIcon.setImageResource(iconRes)
        }

        private fun getTimeAgo(date: LocalDateTime): String {
            val now = LocalDateTime.now()
            val duration = Duration.between(date, now)

            return when {
                duration.toMinutes() < 1 -> "Just now"
                duration.toHours() < 1 -> {
                    val minutes = duration.toMinutes()
                    "$minutes min ago"
                }
                duration.toDays() < 1 -> {
                    val hours = duration.toHours()
                    "$hours h ago"
                }
                else -> {
                    val days = duration.toDays()
                    "$days d ago"
                }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem == newItem
        }
    }
}