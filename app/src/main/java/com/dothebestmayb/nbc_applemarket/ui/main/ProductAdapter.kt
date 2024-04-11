package com.dothebestmayb.nbc_applemarket.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dothebestmayb.nbc_applemarket.R
import com.dothebestmayb.nbc_applemarket.data.LikeManager
import com.dothebestmayb.nbc_applemarket.data.LoggedUserManager
import com.dothebestmayb.nbc_applemarket.databinding.ItemProductOverviewBinding
import com.dothebestmayb.nbc_applemarket.model.Product
import com.dothebestmayb.nbc_applemarket.model.ProductChangePayload
import com.dothebestmayb.nbc_applemarket.util.toStringWithComma

class ProductAdapter(
    private val onClickListener: ProductOnClickListener,
) : ListAdapter<Product, ProductAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(private val binding: ItemProductOverviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) = with(binding) {
            setData(product)
            setVisibility(product)
            setListener(product)
        }

        private fun setData(product: Product) = with(binding) {
            ivThumbnail.setImageURI(product.imageUri)
            tvName.text = product.name
            tvLocation.text = product.location
            tvPrice.text = product.price.toStringWithComma()

            updateLikeFilled(product)
        }

        fun updateLikeFilled(product: Product) {
            val id = if (LikeManager.checkLike(LoggedUserManager.loggedUser, product)) {
                R.drawable.like_fill
            } else {
                R.drawable.like
            }
            binding.ivLike.setImageResource(id)
        }

        private fun setVisibility(product: Product) = with(binding) {
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

        private fun setListener(product: Product) {
            binding.root.setOnClickListener {
                onClickListener.onClick(product)
            }
            binding.root.setOnLongClickListener {
                onClickListener.onLongClick(product)
                return@setOnLongClickListener true
            }
        }

        fun updateLike(count: Int) {
            binding.tvLike.text = count.toString()
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }
        val product = getItem(position)
        for (payloadLists in payloads) {
            for (payload in payloadLists as List<*>) {
                when(payload) {
                    ProductChangePayload.LIKE -> holder.updateLike(product.like)
                    ProductChangePayload.LIKED_FILLED -> holder.updateLikeFilled(product)
                }
            }
        }

    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.uuid == newItem.uuid
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: Product, newItem: Product): Any {
                val changes = mutableListOf<ProductChangePayload>()

                if (oldItem.like != newItem.like) {
                    changes.add(ProductChangePayload.LIKE)
                }
                changes.add(ProductChangePayload.LIKED_FILLED)

                return changes
            }
        }
    }
}