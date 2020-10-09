package com.example.restaurantapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.MainActivity
import com.example.restaurantapp.R
import com.example.restaurantapp.databinding.FragmentOrdersBinding
import com.example.restaurantapp.models.Order
import com.example.restaurantapp.recyclerview.OrdersListAdapter
import com.example.restaurantapp.repository.RestaurantRepository

class OrdersFragment: Fragment() {

    private lateinit var binding: FragmentOrdersBinding
    private val ordersListAdapter = OrdersListAdapter() {
        onOrderClicked(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            if(RestaurantRepository.getOrders().isNullOrEmpty()) {
                ordersTitle.text = resources.getString(R.string.orders_empty)
            } else {
                ordersTitle.text = resources.getString(R.string.orders)
            }
            ordersList.adapter = ordersListAdapter
            ordersList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        ordersListAdapter.submitList(RestaurantRepository.getOrders())
    }
    private fun onOrderClicked(order: Order) {
        RestaurantRepository.setCurrentOrder(order)
        (activity as MainActivity).swapFragments(OrderDetailsFragment(), "ORDER_DETAILS")
    }
}