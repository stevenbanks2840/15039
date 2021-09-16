package com.ftresearch.cakes.ui.cakedetail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ftresearch.cakes.R
import com.ftresearch.cakes.databinding.FragmentCakeDetailBinding
import dagger.android.support.DaggerFragment

class CakeDetailFragment : DaggerFragment() {

    private lateinit var binding: FragmentCakeDetailBinding

    private val args: CakeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCakeDetailBinding.inflate(layoutInflater)

        val transition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = null

        postponeEnterTransition()

        binding.cakeDetailDescription.text = args.cake.desc
        binding.cakeDetailImage.transitionName = args.cake.title

        Glide.with(this) // TODO: Reduce hardcoded reference to 3rd party library by injecting a wrapper
            .load(args.cake.image)
            .placeholder(R.drawable.placeholder_image)
            .override(1000, 1000) // TODO: Scaling/aspect ratio
            .centerCrop()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(binding.cakeDetailImage)

        return binding.root
    }
}
