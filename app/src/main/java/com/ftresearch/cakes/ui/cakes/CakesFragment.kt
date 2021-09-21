package com.ftresearch.cakes.ui.cakes

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.ftresearch.cakes.R
import com.ftresearch.cakes.databinding.FragmentCakesBinding
import com.ftresearch.cakes.extensions.exhaustive
import com.ftresearch.cakes.extensions.setupActionBarNavigation
import com.ftresearch.cakes.repository.Cake
import com.ftresearch.cakes.sync.CakeSyncState
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CakesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModeFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentCakesBinding

    private val adapter = CakeAdapter(::showCake)

    private val viewModel: CakesViewModel by viewModels {
        viewModeFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCakesBinding.inflate(layoutInflater)
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.refresh() }
        binding.recyclerView.adapter = adapter

        viewModel.cakes.observe(viewLifecycleOwner, { state ->
            adapter.submitList(state)
        })

        viewModel.cakeSyncState.observe(viewLifecycleOwner, { state ->
            render(state)
        })

        postponeEnterTransition()

        binding.root.doOnPreDraw {
            startPostponedEnterTransition()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        setupActionBarNavigation(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                findNavController().navigate(CakesFragmentDirections.actionCakesToCakeSearch())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun render(state: CakeSyncState) {
        when (state) {
            CakeSyncState.InProgress -> {
                startProgress()
            }
            is CakeSyncState.Complete -> {
                stopProgress()
            }
            is CakeSyncState.Error -> {
                stopProgress()
                showError(state.exception)
            }
        }.exhaustive
    }

    private fun startProgress() {
        if (!binding.swipeRefreshLayout.isRefreshing) binding.progress.show()
    }

    private fun stopProgress() {
        binding.progress.hide()
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun showCake(cake: Cake, sharedImageView: ImageView) {
        sharedImageView.transitionName?.let { transitionName ->
            val extrasBuilder = FragmentNavigator.Extras.Builder()
            extrasBuilder.addSharedElement(sharedImageView, transitionName)
            val extras = extrasBuilder.build()
            findNavController().navigate(
                CakesFragmentDirections.actionCakesToCakeDetail(cake.title, true, cake), extras
            )

            binding.toolbar.isInvisible = true
        }
    }

    private fun showError(exception: Exception) =
        Snackbar.make(
            binding.coordinatorLayout,
            exception.message ?: getString(R.string.error_general),
            Snackbar.LENGTH_SHORT
        ).setAction(R.string.error_retry) {
            viewModel.refresh()
        }.show()
}
