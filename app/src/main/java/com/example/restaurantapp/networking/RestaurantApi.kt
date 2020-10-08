package com.example.restaurantapp.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantApi {

    @GET("/categories")
    fun getCategories(): Call<MenuCategories>

    @GET("/menu")
    fun getMenu(@Query("category") category: String): Call<Menu>
}