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
import com.ftresearch.cakes.extensions.setupActionBar
import dagger.android.support.DaggerFragment

class CakeDetailFragment : DaggerFragment() {

    private lateinit var binding: FragmentCakeDetailBinding

    private val args: CakeDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transition = TransitionInflater.from(context).inflateTransition(R.transition.cake_transition)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition

        postponeEnterTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCakeDetailBinding.inflate(layoutInflater)

        binding.cakeDetailDescription.text = args.cake.desc
        binding.cakeDetailImage.transitionName = args.cake.title
        binding.cakeDetailDetail.text = args.cake.detail

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        setupActionBar(binding.toolbar)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this) // TODO: Reduce hardcoded reference to 3rd party library by injecting a wrapper
            .load(args.cake.image)
            .placeholder(R.drawable.placeholder_image)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?, target: Target<Drawable>?,
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
    }
}
