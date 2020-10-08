package com.example.restaurantapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantapp.R
import com.example.restaurantapp.databinding.FragmentCartBinding
import com.example.restaurantapp.recyclerview.CartItemsListAdapter
import com.example.restaurantapp.repository.RestaurantRepository

class CartFragment: Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val cartItemsListAdapter = CartItemsListAdapter() { name, actionType, itemId ->
        onCartActionClicked(name, actionType, itemId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(RestaurantRepository.getCart().isNotEmpty()) {
            setUINonEmptyCart()
        } else {
            setUiEmptyCart()
        }

        cartItemsListAdapter.submitList(RestaurantRepository.getCart().map { it.key })
    }

    private fun onCartActionClicked(itemName: String, actionType: String, itemId: Int) {
        when (actionType) {
            "add" -> {
                RestaurantRepository.addToCart(itemId, itemName)
            }
            "subtract" -> {
                RestaurantRepository.subtractFromCart(itemId)
            }
            else -> {
                RestaurantRepository.deleteFromCart(itemId)
            }
        }

        cartItemsListAdapter.submitList(RestaurantRepository.getCart().map { it.key })
        cartItemsListAdapter.notifyDataSetChanged()
        if(RestaurantRepository.getCart().isEmpty()) {
            setUiEmptyCart()
        } else {
            setUINonEmptyCart()
        }
    }

    private fun setUiEmptyCart() {
        binding.apply {
            addToCart.isVisible = false
            cartTitle.text = resources.getString(R.string.empty_cart)
            cartTotal.text = ""
        }
    }

    private fun setUINonEmptyCart() {
        binding.apply {
            cartList.adapter = cartItemsListAdapter
            cartList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            cartTotal.text = "Total: ${RestaurantRepository.formatTotal()}"
            addToCart.isVisible = true
            addToCart.setOnClickListener {
                RestaurantRepository.checkout()
                cartItemsListAdapter.submitList(RestaurantRepository.getCart().map { it.key })
                cartItemsListAdapter.notifyDataSetChanged()
                setUiEmptyCart()
            }
        }
    }
}