package com.mtanmay.loopiieinternship.shared

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.mtanmay.loopiieinternship.api.Photo
import com.mtanmay.loopiieinternship.databinding.ImageItemBinding

class Adapter : PagingDataAdapter<Photo, Adapter.ViewHolder>(DIFF_UTIL_CALLBACK) {

    private lateinit var listener: OnItemClickListener

    companion object {
        private val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem.url == newItem.url

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = getItem(position)
        if (currentItem != null)
            holder.bind(currentItem)
    }

    inner class ViewHolder(
        private val binding: ImageItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val shimmer = Shimmer.AlphaHighlightBuilder()
            .setDuration(1000)
            .setBaseAlpha(0.8f)
            .setHighlightAlpha(0.9f)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()

        fun bind(photo: Photo) {

            val shimmerDrawable = ShimmerDrawable().apply {
                setShimmer(shimmer)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(photo.url)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(shimmerDrawable)
                    .into(image)

            }

            itemView.setOnClickListener {
                listener.onItemClick(photo)
            }

        }

    }

    interface OnItemClickListener {
        fun onItemClick(photo: Photo)
    }

    fun setOnItemClickListener(_listener: OnItemClickListener) {
        this.listener = _listener
    }
}