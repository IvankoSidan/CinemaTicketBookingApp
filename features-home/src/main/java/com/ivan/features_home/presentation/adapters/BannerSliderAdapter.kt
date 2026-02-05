package com.ivan.cinematicketbooking.features_home.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.ivan.cinematicketbooking.features_home.R
import com.ivan.cinematicketbooking.features_home.domain.model.Banner

class BannerSliderAdapter(
    private val banners: List<Banner>,
    private val onClick: (Long) -> Unit
) : RecyclerView.Adapter<BannerSliderAdapter.BannerViewHolder>() {

    inner class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageBanner: ImageView = itemView.findViewById(R.id.imageBanner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val banner = banners[position]
        Glide.with(holder.itemView.context)
            .load(banner.imageUrl)
            .transform(CenterCrop(), RoundedCorners(60))
            .into(holder.imageBanner)

        holder.itemView.setOnClickListener {
            banner.movieId?.let { onClick(it) }
        }
    }

    override fun getItemCount(): Int = banners.size
}
