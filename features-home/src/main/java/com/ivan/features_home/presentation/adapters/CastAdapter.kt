package com.ivan.cinematicketbooking.features_home.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.ivan.cinematicketbooking.features_home.R
import com.ivan.cinematicketbooking.features_home.domain.model.Cast


class CastAdapter : ListAdapter<Cast, CastAdapter.CastViewHolder>(
    DiffCallback()
) {

    class CastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image: ShapeableImageView = view.findViewById(R.id.castImage)
        private val name: TextView = view.findViewById(R.id.castNameTextView)

        fun bind(cast: Cast) {
            name.text = cast.actorName
            Glide.with(image.context)
                .load(cast.photoUrl)
                .centerCrop()
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cast, parent, false)
        return CastViewHolder(view)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Cast>() {
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Cast, newItem: Cast) = oldItem == newItem
    }
}
