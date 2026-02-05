package com.ivan.cinematicketbooking.features_home.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.ivan.cinematicketbooking.features_home.R
import com.ivan.cinematicketbooking.features_home.domain.model.Movie


class MovieAdapter(
    private val onItemClick: (Long) -> Unit
) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(
    DiffCallback()
) {

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val poster: ImageView = view.findViewById(R.id.pic)
        val title: TextView = view.findViewById(R.id.nameTxt)

        fun bind(movie: Movie) {
            title.text = movie.title
            Glide.with(itemView.context)
                .load(movie.posterUrl)
                .transform(CenterCrop(), RoundedCorners(15))
                .into(poster)

            itemView.setOnClickListener {
                onItemClick(movie.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_card, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
    }
}
