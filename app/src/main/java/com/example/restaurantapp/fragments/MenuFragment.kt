package com.example.restaurantapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantapp.MainActivity
import com.example.restaurantapp.databinding.FragmentMenuBinding
import com.example.restaurantapp.models.MenuItem
import com.example.restaurantapp.networking.RestaurantNetworking
import com.example.restaurantapp.recyclerview.MenuItemsListAdapter
import com.example.restaurantapp.repository.RestaurantRepository

class MenuFragment: Fragment() {

    private lateinit var binding: FragmentMenuBinding
    private val menuItemsAdapter = MenuItemsListAdapter() {
        onMenuItemClicked(it)
    }
    private val category: String = RestaurantRepository.getCurrentCategory()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RestaurantNetworking.getMenuItems(category) {
            setMenuItems()
        }
    }

    private fun setMenuItems() {

        binding.apply {
            menuTitle.text = category
            menuList.adapter = menuItemsAdapter
            menuList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        menuItemsAdapter.submitList(RestaurantRepository.getCurrentMenuItems())
    }

    private fun onMenuItemClicked(item: MenuItem) {

        RestaurantRepository.setCurrentItem(item)
        (activity as MainActivity).swapFragments(MenuItemFragment(), "MENU_ITEM")
    }
}