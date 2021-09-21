package com.ftresearch.cakes.ui.cakedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import coil.imageLoader
import coil.request.ImageRequest
import com.ftresearch.cakes.R
import com.ftresearch.cakes.databinding.FragmentCakeDetailBinding
import com.ftresearch.cakes.extensions.setupActionBarNavigation
import dagger.android.support.DaggerFragment

class CakeDetailFragment : DaggerFragment() {

    private lateinit var binding: FragmentCakeDetailBinding

    private val args: CakeDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (args.showTransition) {
            val transition = TransitionInflater.from(context).inflateTransition(R.transition.cake_transition)
            sharedElementEnterTransition = transition
            sharedElementReturnTransition = transition

            postponeEnterTransition()
        }
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

        setupActionBarNavigation(binding.toolbar)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val request = ImageRequest.Builder(requireContext())
            .data(args.cake.image)
            .placeholder(R.drawable.placeholder_image)
            .target(
                onSuccess = { result ->
                    binding.cakeDetailImage.setImageDrawable(result)

                    startPostponedEnterTransition()
                },
                onError = {
                    startPostponedEnterTransition()
                }
            )
            .build()

        requireContext().imageLoader.enqueue(request)
    }
}
