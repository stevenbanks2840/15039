package com.ftresearch.cakes.ui.cakes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
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
            cakeDescription.text = cake.desc
            cakeImage.load(cake.image) {
                crossfade(300)
                placeholder(R.drawable.placeholder_image)
            }

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
