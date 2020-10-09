package com.example.restaurantapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.databinding.ItemCartItemsBinding
import com.example.restaurantapp.repository.RestaurantRepository

class CartItemsListAdapter(val onClick: (itemName: String, actionType: String, id: Int) -> Unit): ListAdapter<Int, CartItemsListAdapter.CartItemsViewHolder>(
    diffUtil
) {

    companion object {
        private val diffUtil = object: DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int) = true

            override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartItemsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartItemsBinding.inflate(inflater, parent, false)
        return CartItemsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartItemsViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.onBind(item) { name, type, id ->
            onClick(name, type, id)
        }
    }

    class CartItemsViewHolder(private val binding: ItemCartItemsBinding): RecyclerView.ViewHolder(
        binding.root
    ) {
        fun onBind(item: Int, onClick: (itemName: String, actionType: String, id: Int) -> Unit) {

            val items = RestaurantRepository.getCart()
            val itemCount = items[item]?.get(1)
            val itemName = items[item]?.get(0) ?: ""

            binding.apply {
                cartItemName.text = itemName
                cartItemCount.text = itemCount
                subtract.setOnClickListener {
                    onClick(itemName, "subtract", item)
                }

                add.setOnClickListener {
                    onClick(itemName, "add", item)
                }

                delete.setOnClickListener {
                    onClick(itemName, "delete", item)
                }
            }
        }
    }
}