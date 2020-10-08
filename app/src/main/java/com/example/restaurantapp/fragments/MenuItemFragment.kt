package com.example.restaurantapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.restaurantapp.databinding.FragmentMenuItemBinding
import com.example.restaurantapp.repository.RestaurantRepository

class MenuItemFragment: Fragment() {

    private lateinit var binding: FragmentMenuItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMenuItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuItem = RestaurantRepository.getCurrentItem()

        binding.apply {
            addToCart.setOnClickListener {
                RestaurantRepository.addToCart(menuItem.id, menuItem.name, menuItem.price)
            }
            itemCategory.text = "Category: ${menuItem.category}"
            itemName.text = menuItem.name
            itemDesc.text = menuItem.description
            itemPrice.text = "%.2f".format(menuItem.price)
        }
    }
}