package com.ftresearch.cakes.ui.cakes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ftresearch.cakes.R
import com.ftresearch.cakes.databinding.ListItemCakeBinding
import com.ftresearch.cakes.rest.cake.Cake

class CakeAdapter(private val onClick: (Cake, ImageView) -> Unit) :
    ListAdapter<Cake, CakeAdapter.ViewHolder>(CakeDiffCallback) {

    inner class ViewHolder(val binding: ListItemCakeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(container: ViewGroup, position: Int): ViewHolder {
        val rootView = LayoutInflater
            .from(container.context)
            .inflate(R.layout.list_item_cake, container, false)

        return ViewHolder(ListItemCakeBinding.bind(rootView))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cake = getItem(position)

        holder.binding.apply {
            cakeTitle.text = cake.title
            cakeImage.transitionName = cake.title

            Glide.with(root.context)
                .load(cake.image)
                .placeholder(R.drawable.placeholder_image)
                .override(64, 64) // TODO: Use thumbnails and scaling/aspect ratio
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(cakeImage)

            root.setOnClickListener { onClick(cake, cakeImage) }
        }
    }
}

object CakeDiffCallback : DiffUtil.ItemCallback<Cake>() {
    override fun areItemsTheSame(oldItem: Cake, newItem: Cake): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Cake, newItem: Cake): Boolean {
        return oldItem.title == newItem.title
    }
}
