package com.example.restaurantapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantapp.MainActivity
import com.example.restaurantapp.databinding.FragmentCategoriesBinding
import com.example.restaurantapp.networking.RestaurantNetworking
import com.example.restaurantapp.recyclerview.CategoriesListAdapter
import com.example.restaurantapp.repository.RestaurantRepository

class CategoriesFragment: Fragment() {

    companion object {
        var currentCategory: String = String()
    }
    private lateinit var binding: FragmentCategoriesBinding
    private val categoriesAdapter = CategoriesListAdapter() {
        onCategoryClicked(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRestaurantCategories()
    }

    private fun setRestaurantCategories() {
        RestaurantNetworking.getCategories {

            binding.categoriesList.apply {
                adapter = categoriesAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }

            categoriesAdapter.submitList(RestaurantRepository.getCategories())
        }
    }

    private fun onCategoryClicked(category: String) {
        RestaurantRepository.setCurrentCategory(category)
        (activity as MainActivity).swapFragments(MenuFragment(), "MENU_ITEMS")
    }
}