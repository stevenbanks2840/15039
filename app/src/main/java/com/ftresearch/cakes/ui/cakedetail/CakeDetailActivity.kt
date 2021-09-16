package com.ftresearch.cakes.ui.cakedetail

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ftresearch.cakes.R
import com.ftresearch.cakes.rest.cake.Cake
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_cake_detail.*

class CakeDetailActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_cake_detail)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        supportPostponeEnterTransition()

        val cake = intent?.extras?.getSerializable(EXTRA_CAKE) as Cake

        title = cake.title
        cakeDetailDescription.text = cake.desc
        cakeDetailImage.transitionName = cake.title

        Glide.with(this) // TODO: Reduce hardcoded reference to 3rd party library by injecting a wrapper
                .load(cake.image)
                .placeholder(R.drawable.placeholder_image)
                .override(1000, 1000) // TODO: Scaling/aspect ratio
                .centerCrop()
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return true
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }
                })
                .into(cakeDetailImage)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finishAfterTransition()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {

        private const val EXTRA_CAKE = "cakeExtra"

        fun createIntent(context: Context, cake: Cake): Intent =
                Intent(context, CakeDetailActivity::class.java).putExtra(EXTRA_CAKE, cake)
    }
}
