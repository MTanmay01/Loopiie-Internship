package com.mtanmay.loopiieinternship.ui.imagescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.mtanmay.loopiieinternship.R
import com.mtanmay.loopiieinternship.api.Photo
import com.mtanmay.loopiieinternship.databinding.FragmentImageScreenBinding


class ImageScreen : Fragment(R.layout.fragment_image_screen) {

    private lateinit var binding: FragmentImageScreenBinding
    private val args: ImageScreenArgs by navArgs()
    private lateinit var mImage: Photo

    private val shimmer = Shimmer.AlphaHighlightBuilder()
        .setDuration(1000)
        .setBaseAlpha(0.8f)
        .setHighlightAlpha(0.9f)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageScreenBinding.inflate(inflater, container, false)

        mImage = args.image
        val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(shimmer)
        }

        binding.apply {
            Glide.with(requireContext())
                .load(mImage.url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(shimmerDrawable)
                .into(loadImage)

        }

        return binding.root
    }

}