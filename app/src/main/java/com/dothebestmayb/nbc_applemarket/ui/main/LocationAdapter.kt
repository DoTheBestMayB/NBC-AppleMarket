package com.dothebestmayb.nbc_applemarket.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dothebestmayb.nbc_applemarket.R
import com.dothebestmayb.nbc_applemarket.databinding.ItemLocationBinding
import com.dothebestmayb.nbc_applemarket.model.LocationItem
import com.dothebestmayb.nbc_applemarket.model.LocationItemOrder

class LocationAdapter(private val onClickListener: LocationOnClickListener) :
    ListAdapter<LocationItem, LocationAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(location: LocationItem, order: LocationItemOrder) = with(binding) {
            tvLocation.text = location.name
            updateColor(location.isSelected)

            root.setOnClickListener {
                onClickListener.onClick(location)
            }
            root.background = when (order) {
                LocationItemOrder.FIRST -> AppCompatResources.getDrawable(root.context, R.drawable.location_border_first)
                LocationItemOrder.MIDDLE -> AppCompatResources.getDrawable(root.context, R.drawable.location_border_middle)
                LocationItemOrder.END -> AppCompatResources.getDrawable(root.context, R.drawable.location_border_end)
                LocationItemOrder.SOLE -> AppCompatResources.getDrawable(root.context, R.drawable.location_border_sole)
            }
        }

        fun updateColor(isSelected: Boolean) = with(binding) {
            val color = if (isSelected) {
                ContextCompat.getColor(root.context, R.color.black)
            } else {
                ContextCompat.getColor(root.context, R.color.gray_300)
            }
            tvLocation.setTextColor(color)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationAdapter.ViewHolder {
        return ViewHolder(
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: LocationAdapter.ViewHolder, position: Int) {
        val order = when {
            itemCount == 1 -> LocationItemOrder.SOLE
            itemCount >= 2 && position == 0 -> LocationItemOrder.FIRST
            itemCount - 1 == position -> LocationItemOrder.END
            else -> LocationItemOrder.MIDDLE
        }
        holder.bind(getItem(position), order)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }
        for (payload in payloads) {
            holder.updateColor(payload as Boolean)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<LocationItem>() {
            override fun areItemsTheSame(oldItem: LocationItem, newItem: LocationItem): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: LocationItem, newItem: LocationItem): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: LocationItem, newItem: LocationItem): Any {
                return newItem.isSelected
            }
        }
    }
}
