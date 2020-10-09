package com.example.restaurantapp

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.restaurantapp.databinding.ActivityMainBinding
import com.example.restaurantapp.fragments.CartFragment
import com.example.restaurantapp.fragments.CategoriesFragment
import com.example.restaurantapp.fragments.OrdersFragment
import com.example.restaurantapp.repository.RestaurantRepository
import com.google.android.material.badge.BadgeDrawable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var badge: BadgeDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.setOnNavigationItemSelectedListener{
            handleBottomNavigation(it.itemId)
        }
        badge = binding.bottomNavigation.getOrCreateBadge(R.id.cart)
        badge.isVisible = false
        badge.number = 0
        binding.bottomNavigation.selectedItemId = R.id.menu

        RestaurantRepository.setBaseUrl(getString(R.string.ipaddress))
    }

    private fun handleBottomNavigation(
        menuItemId: Int,
    ) : Boolean = when(menuItemId) {
        R.id.menu -> {
            swapFragments(CategoriesFragment(), "CATEGORIES")
            true
        }
        R.id.orders -> {
            swapFragments(OrdersFragment(), "ORDERS")
            true
        }
        R.id.cart -> {
            swapFragments(CartFragment(), "CART")
            updateBadge("clear")
            true
        }
        else -> false
    }

    fun swapFragments(fragment: Fragment, tag: String) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, tag)
            .addToBackStack(null)
            .commit()

    }

    fun updateBadge(actionType: String) {
        if(actionType == "add") {
            badge.isVisible = true
            badge.number++
        }
        else {
            badge.number = 0
            badge.isVisible = false
        }
    }

    override fun onBackPressed() {

        if(binding.bottomNavigation.selectedItemId == R.id.menu) {
            supportFragmentManager.popBackStack()
        }
    }
}