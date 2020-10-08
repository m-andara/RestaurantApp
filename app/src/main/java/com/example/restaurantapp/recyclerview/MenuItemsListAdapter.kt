package com.example.restaurantapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.databinding.ItemMenuItemsBinding
import com.example.restaurantapp.models.MenuItem

class MenuItemsListAdapter(val onClick: (MenuItem) -> Unit): ListAdapter<MenuItem, MenuItemsListAdapter.MenuItemsViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object: DiffUtil.ItemCallback<MenuItem>() {
            override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean = true

            override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMenuItemsBinding.inflate(inflater, parent, false)
        return MenuItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuItemsViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    class MenuItemsViewHolder(private val binding: ItemMenuItemsBinding): RecyclerView.ViewHolder(binding.root){

        fun onBind(item: MenuItem) {
            binding.apply {
                menuItemName.text = item.name
            }
        }
    }
}