package com.ftresearch.cakes.ui.cakesearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ftresearch.cakes.R
import com.ftresearch.cakes.databinding.ListItemCakeSearchBinding
import com.ftresearch.cakes.repository.Cake

class CakeSearchAdapter(private val onClick: (Cake) -> Unit) :
    ListAdapter<Cake, CakeSearchAdapter.ViewHolder>(CakeDiffCallback) {

    inner class ViewHolder(val binding: ListItemCakeSearchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(container: ViewGroup, position: Int): ViewHolder {
        val rootView = LayoutInflater
            .from(container.context)
            .inflate(R.layout.list_item_cake_search, container, false)

        return ViewHolder(ListItemCakeSearchBinding.bind(rootView))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cake = getItem(position)

        holder.binding.apply {
            cakeTitle.text = cake.title
            cakeDescription.text = cake.desc

            root.setOnClickListener { onClick(cake) }
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
