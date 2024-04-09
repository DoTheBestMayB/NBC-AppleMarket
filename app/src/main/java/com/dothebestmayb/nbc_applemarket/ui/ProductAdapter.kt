package com.dothebestmayb.nbc_applemarket.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dothebestmayb.nbc_applemarket.databinding.ItemProductOverviewBinding
import com.dothebestmayb.nbc_applemarket.model.Product
import com.dothebestmayb.nbc_applemarket.util.toStringWithComma

class ProductAdapter : ListAdapter<Product, ProductAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(private val binding: ItemProductOverviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) = with(binding) {
            ivThumbnail.setImageURI(product.imageUri)
            tvName.text = product.name
            tvLocation.text = product.location
            tvPrice.text = product.price.toStringWithComma()

            val chatVisibility = if (product.numberOfChat == 0) {
                View.GONE
            } else {
                tvChat.text = product.numberOfChat.toString()
                View.VISIBLE
            }
            tvChat.visibility = chatVisibility
            ivChat.visibility = chatVisibility

            val likeVisibility = if (product.like == 0) {
                View.GONE
            } else {
                tvLike.text = product.like.toString()
                View.VISIBLE
            }
            tvLike.visibility = likeVisibility
            ivChat.visibility = likeVisibility
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductOverviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.uuid == newItem.uuid
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }
}