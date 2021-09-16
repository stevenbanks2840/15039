package com.ftresearch.cakes.ui.cakes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ftresearch.cakes.databinding.FragmentCakesBinding
import com.ftresearch.cakes.rest.cake.Cake
import com.ftresearch.cakes.ui.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CakesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModeFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentCakesBinding

    private val cakeAdapter = CakeAdapter(::showCake)

    private val viewModel: CakesViewModel by viewModels {
        viewModeFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCakesBinding.inflate(layoutInflater)

        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.refresh() }

        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = cakeAdapter
            addItemDecoration(dividerItemDecoration)
        }

        viewModel.cakes.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> startProgress()
                Resource.Status.SUCCESS -> {
                    stopProgress()
                    it.data?.let { cakes -> populateCakes(cakes) }
                }
                Resource.Status.ERROR -> {
                    stopProgress()
                    showError(it.message!!)
                }
            }
        })

        viewModel.init()

        return binding.root
    }

    private fun startProgress() {
        if (!binding.swipeRefreshLayout.isRefreshing) binding.progress.show()
    }

    private fun stopProgress() {
        binding.progress.hide()
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun populateCakes(cakes: List<Cake>) {
        cakeAdapter.submitList(cakes)
    }

    private fun showCake(cake: Cake, sharedImageView: ImageView) {
        sharedImageView.transitionName?.let { transitionName ->
            val extrasBuilder = FragmentNavigator.Extras.Builder()
            extrasBuilder.addSharedElement(sharedImageView, transitionName)
            val extras = extrasBuilder.build()

            findNavController().navigate(
                CakesFragmentDirections.actionCakesToCakeDetail(cake.title, cake),
                extras
            )
        }
    }

    private fun showError(message: String) =
        Snackbar.make(binding.coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") {
                viewModel.refresh()
            }.show()
}
