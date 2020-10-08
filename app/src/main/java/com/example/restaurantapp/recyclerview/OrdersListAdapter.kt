package com.example.restaurantapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.databinding.ItemOrdersBinding
import com.example.restaurantapp.models.Order
import com.example.restaurantapp.utils.DateUtils

class OrdersListAdapter(val onClick: (Order) -> Unit): ListAdapter<Order, OrdersListAdapter.OrdersViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object: DiffUtil.ItemCallback<Order>() {
            override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean = true

            override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrdersBinding.inflate(inflater, parent, false)
        return OrdersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    class OrdersViewHolder(private val binding: ItemOrdersBinding): RecyclerView.ViewHolder(binding.root){

        fun onBind(item: Order) {

            val formattedDate = DateUtils.formatDate(item.date)
            binding.apply {
               orderTitle.text = formattedDate
            }
        }
    }
}