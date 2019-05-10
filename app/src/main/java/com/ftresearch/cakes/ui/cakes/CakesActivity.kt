package com.ftresearch.cakes.ui.cakes

import android.app.ActivityOptions
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ftresearch.cakes.R
import com.ftresearch.cakes.rest.cake.Cake
import com.ftresearch.cakes.ui.Resource
import com.ftresearch.cakes.ui.cakedetail.CakeDetailActivity
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_cakes.*
import javax.inject.Inject

class CakesActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModeFactory: ViewModelProvider.Factory

    private val cakeAdapter = CakeAdapter(::showCake)
    private lateinit var viewModel: CakesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_cakes)

        swipeRefreshLayout.setOnRefreshListener { viewModel.refresh() }

        val dividerItemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = cakeAdapter
            addItemDecoration(dividerItemDecoration)
        }

        viewModel = ViewModelProviders.of(this, viewModeFactory).get(CakesViewModel::class.java)
                .apply { init() }

        viewModel.cakes.observe(this, Observer {
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
    }

    private fun startProgress() {
        if (!swipeRefreshLayout.isRefreshing) progress.show()
    }

    private fun stopProgress() {
        progress.hide()
        swipeRefreshLayout.isRefreshing = false
    }

    private fun populateCakes(cakes: List<Cake>) {
        cakeAdapter.cakes = cakes
        recyclerView.scheduleLayoutAnimation()
    }

    private fun showCake(cake: Cake, sharedImageView: ImageView) {
        sharedImageView.transitionName?.let {
            val options = ActivityOptions.makeSceneTransitionAnimation(this, sharedImageView, it)
            val intent = CakeDetailActivity.createIntent(this, cake)
            startActivity(intent, options.toBundle())
        }
    }

    private fun showError(message: String) =
            Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry") {
                        viewModel.refresh()
                    }.show()
}
