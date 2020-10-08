package com.example.restaurantapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.databinding.ItemCategoriesBinding

class CategoriesListAdapter(val onClick: (String) -> Unit): ListAdapter<String, CategoriesListAdapter.CategoriesViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object: DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) = true

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoriesBinding.inflate(inflater, parent, false)
        return CategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoriesViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.onBind(item)
        holder.itemView.setOnClickListener { onClick(item) }
    }

    class CategoriesViewHolder(private val binding: ItemCategoriesBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: String) {
            binding.apply {
                categoryName.text = item
            }
        }
    }
}