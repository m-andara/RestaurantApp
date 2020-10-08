package com.example.restaurantapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.restaurantapp.databinding.ActivityMainBinding
import com.example.restaurantapp.fragments.CartFragment
import com.example.restaurantapp.fragments.CategoriesFragment
import com.example.restaurantapp.fragments.OrdersFragment
import com.example.restaurantapp.repository.RestaurantRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener{
            handleBottomNavigation(it.itemId)
        }
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

    override fun onBackPressed() {

        if(binding.bottomNavigation.selectedItemId == R.id.menu) {
            supportFragmentManager.popBackStack()
        }
    }
}