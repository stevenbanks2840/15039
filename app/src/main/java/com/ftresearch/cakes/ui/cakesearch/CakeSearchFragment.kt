package com.ftresearch.cakes.ui.cakesearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ftresearch.cakes.databinding.FragmentCakeSearchBinding
import com.ftresearch.cakes.extensions.setupActionBarNavigation
import com.ftresearch.cakes.extensions.showKeyboard
import com.ftresearch.cakes.repository.Cake
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CakeSearchFragment : DaggerFragment() {

    @Inject
    lateinit var viewModeFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentCakeSearchBinding

    private val adapter = CakeSearchAdapter(::showCake)

    private val viewModel: CakesSearchViewModel by viewModels {
        viewModeFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCakeSearchBinding.inflate(layoutInflater)
        binding.recyclerView.adapter = adapter

        viewModel.searchResults.observe(viewLifecycleOwner, { state ->
            adapter.submitList(state)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupActionBarNavigation(binding.toolbar)

        binding.searchView.requestFocus()
        binding.searchView.setOnQueryTextFocusChangeListener { searchView, hasFocus ->
            if (hasFocus) {
                searchView.showKeyboard()
            }
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.search(newText ?: "")
                return true
            }
        })
    }

    private fun showCake(cake: Cake) {
        findNavController().navigate(
            CakeSearchFragmentDirections.actionCakesSearchToCakeDetail(cake.title, false, cake)
        )
    }
}
