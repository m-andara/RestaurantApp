package com.example.restaurantapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantapp.databinding.FragmentOrderDetailsBinding
import com.example.restaurantapp.recyclerview.OrderDetailsListAdapter
import com.example.restaurantapp.repository.RestaurantRepository
import com.example.restaurantapp.utils.DateUtils

class OrderDetailsFragment: Fragment() {

    lateinit var binding: FragmentOrderDetailsBinding
    private val orderDetailsListAdapter = OrderDetailsListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val order = RestaurantRepository.getCurrentOrder()

        binding.apply {
            orderDate.text = DateUtils.formatDate(order.date)
            orderTotal.text = "$${order.total}"
            orderItemsList.adapter = orderDetailsListAdapter
            orderItemsList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        orderDetailsListAdapter.submitList(RestaurantRepository.getCurrentOrder().items.map { it.key })
    }
}