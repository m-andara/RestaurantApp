package com.example.restaurantapp.networking

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.restaurantapp.models.MenuItem
import com.example.restaurantapp.repository.RestaurantRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
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

    fun getImage(url: String, onLoad: (Bitmap) -> Unit) {
        val finalUrl = RestaurantRepository.getBaseUrl() + url.substring(21)
        val request = Request.Builder().url(finalUrl).build()
        client.newCall(request).enqueue(object: okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.v("Networking", "Error: $e")
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (!response.isSuccessful){}
                val bitmap = BitmapFactory.decodeStream(response.body?.byteStream())
                Handler(Looper.getMainLooper()).post { onLoad(bitmap) }
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