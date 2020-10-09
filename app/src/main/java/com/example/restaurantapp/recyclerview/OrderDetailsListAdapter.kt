package com.example.restaurantapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.databinding.ItemOrdersItemsBinding
import com.example.restaurantapp.repository.RestaurantRepository

class OrderDetailsListAdapter: ListAdapter<Int, OrderDetailsListAdapter.OrderDetailsViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object: DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean = true

            override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrdersItemsBinding.inflate(inflater, parent, false)
        return OrderDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }

    class OrderDetailsViewHolder(private val binding: ItemOrdersItemsBinding): RecyclerView.ViewHolder(binding.root){

        fun onBind(item: Int) {
            val items = RestaurantRepository.getCurrentOrder().items
            binding.apply {
                orderItemName.text = items[item]?.get(0)
                orderItemCount.text = "x${items[item]?.get(1)}"
            }
        }
    }
}