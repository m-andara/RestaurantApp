package com.example.restaurantapp.models

data class MenuItem(
    var id: Int,
    var name: String,
    var description: String,
    var price: Double,
    var category: String,
    var imageUrl: String
)