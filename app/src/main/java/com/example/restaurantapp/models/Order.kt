package com.example.restaurantapp.models

import java.time.LocalDateTime

data class Order(
    var date: LocalDateTime,
    var items: MutableMap<Int, Array<String>>,
    var total: String
)