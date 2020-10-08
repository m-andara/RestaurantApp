package com.example.restaurantapp.networking

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Menu (
    @Json(name = "items") val items: List<MenuItems>
)

@JsonClass(generateAdapter = true)
data class MenuItems (
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String,
    @Json(name = "price") val price: Double,
    @Json(name = "category") val category: String,
    @Json(name = "image_url") val imageUrl: String
)

@JsonClass(generateAdapter = true)
data class MenuCategories (
    @Json(name = "categories") val categories: List<String>
)