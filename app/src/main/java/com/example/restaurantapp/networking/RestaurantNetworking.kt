package com.example.restaurantapp.networking

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.restaurantapp.models.MenuItem
import com.example.restaurantapp.repository.RestaurantRepository
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RestaurantNetworking {

    private val client = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
        .writeTimeout(5, TimeUnit.MINUTES) // write timeout
        .readTimeout(5, TimeUnit.MINUTES) // read timeout
        .build();

    private val restaurantApi: RestaurantApi
    get() {
        return Retrofit.Builder()
            .baseUrl(RestaurantRepository.getBaseUrl())
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(RestaurantApi::class.java)
    }

    fun getCategories(onFinished: () -> Unit ) {
        restaurantApi.getCategories().enqueue(object: Callback<MenuCategories> {
            override fun onResponse(
                call: Call<MenuCategories>,
                response: Response<MenuCategories>
            ) {
                val categories = response.body()?.categories ?: emptyList()
                RestaurantRepository.setCategories(categories)
                onFinished()
            }

            override fun onFailure(call: Call<MenuCategories>, t: Throwable) {
                Log.v("Networking", "Error: $t")
            }

        })
    }

    fun getMenuItems(category: String, onFinished: () -> Unit) {
        restaurantApi.getMenu(category).enqueue(object: Callback<Menu> {
            override fun onResponse(call: Call<Menu>, response: Response<Menu>) {
                val menuItems = response.body()?.items?.map { it ->
                    it.toModel()
                } ?: emptyList()
                RestaurantRepository.setCurrentMenuItems(menuItems)
                onFinished()
            }

            override fun onFailure(call: Call<Menu>, t: Throwable) {
                Log.v("Networking", "Error: $t")
            }

        })
    }

    private fun MenuItems.toModel(): MenuItem {
        return MenuItem(
            id = id,
            name = name,
            description = description,
            price = price,
            category = category,
            imageUrl = imageUrl
        )
    }
}