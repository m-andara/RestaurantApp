package com.example.restaurantapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.restaurantapp.R
import com.example.restaurantapp.databinding.FragmentCartBinding
import com.example.restaurantapp.recyclerview.CartItemsListAdapter
import com.example.restaurantapp.repository.RestaurantRepository


class CartFragment: Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val cartItemsListAdapter = CartItemsListAdapter() { name, actionType, itemId ->
        onCartActionClicked(name, actionType, itemId)
    }

    private var itemTouchHelper = ItemTouchHelper(
        object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: ViewHolder, target: ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                val id = RestaurantRepository.getCart().map { it.key }[viewHolder.adapterPosition]
                RestaurantRepository.deleteFromCart(id)
                updateCartAdapter()
            }
        })

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
        itemTouchHelper.attachToRecyclerView(binding.cartList)
        updateCartAdapter()
    }

    private fun updateCartAdapter() {
        if(RestaurantRepository.getCart().isNotEmpty()) {
            setUINonEmptyCart()
        } else {
            setUiEmptyCart()
        }

        cartItemsListAdapter.submitList(RestaurantRepository.getCartItemsId())
        cartItemsListAdapter.notifyDataSetChanged()
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

        updateCartAdapter()
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
            cartList.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            cartTotal.text = "Total: $${RestaurantRepository.formatTotal()}"
            addToCart.isVisible = true
            addToCart.setOnClickListener {
                RestaurantRepository.checkout()
                updateCartAdapter()
            }
        }
    }
}