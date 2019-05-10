package com.ftresearch.cakes.ui.cakes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ftresearch.cakes.R
import com.ftresearch.cakes.rest.cake.Cake
import kotlinx.android.synthetic.main.list_item_cake.view.*

class CakeAdapter(private val onClick: (Cake) -> Unit) : RecyclerView.Adapter<CakeAdapter.ViewHolder>() {

    var cakes: List<Cake> = emptyList()
        set(value) {
            field = value

            notifyDataSetChanged() // TODO: Migrate to ListAdapter for item animations and performance
        }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(container: ViewGroup, position: Int): ViewHolder {
        val rootView = LayoutInflater.from(container.context).inflate(R.layout.list_item_cake, container, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount() = cakes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cake = cakes[position]

        holder.itemView.apply {
            cakeTitle.text = cake.title

            Glide.with(this)
                    .load(cake.image)
                    .placeholder(R.drawable.placeholder_image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(cakeImage)

            setOnClickListener { onClick(cake) }
        }
    }
}
