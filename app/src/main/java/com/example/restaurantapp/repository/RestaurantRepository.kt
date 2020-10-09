package com.example.restaurantapp.repository

import com.example.restaurantapp.models.MenuItem
import com.example.restaurantapp.models.Order
import com.example.restaurantapp.utils.DateUtils

object RestaurantRepository {

    private var BASE_URL = ""
    private var categories = mutableListOf<String>()
    private var currentMenu = mutableListOf<MenuItem>()
private var currentItem = MenuItem(
        0,
        "",
        "description",
        0.00,
        "",
        ""
    )
    private var currentCategory = ""
    private var orders = mutableListOf<Order>()
    private var cartItems = mutableMapOf<Int, Array<String>>()
    private var cartTotal: Double = 0.00
    private var currentOrder = Order(
        DateUtils.getCurrentDate(),
        mutableMapOf(),
        ""
    )

    fun setCurrentOrder(order: Order) {
        currentOrder.date = order.date
        currentOrder.items = order.items.toMap()
        currentOrder.total = order.total
    }

    fun getCurrentOrder(): Order {
        return currentOrder
    }

    private fun changeTotal(amount: Double, actionType: String) {
        if(actionType == "add") cartTotal += amount
        else cartTotal -= amount
    }

    private fun addOrder(item: Order) {
        orders.add(item)
        resetCart()
    }

    private fun resetCart() {
        cartItems.clear()
        cartTotal = 0.00
    }

    fun getOrders(): List<Order> {
        return orders
    }

    fun getCurrentCategory(): String {
        return currentCategory
    }

    fun setCurrentCategory(category: String) {
        currentCategory = category
    }

    fun getCurrentItem(): MenuItem {
        return currentItem
    }

    fun setCurrentItem(item: MenuItem) {
        currentItem.name = item.name
        currentItem.category = item.category
        currentItem.description = item.description
        currentItem.id = item.id
        currentItem.price = item.price
        currentItem.imageUrl = item.imageUrl
    }

    fun setCategories(items: List<String>) {
        categories.clear()
        categories.addAll(items)
    }

    fun getCategories(): List<String> {
        return categories
    }

    fun setCurrentMenuItems(items: List<MenuItem>) {
        currentMenu.clear()
        currentMenu.addAll(items)
    }

    fun getCurrentMenuItems(): List<MenuItem> {
        return currentMenu
    }

    fun getBaseUrl(): String {
        return BASE_URL
    }

    fun setBaseUrl(ip: String) {
        BASE_URL = "http://$ip:8090"
    }

    fun addToCart(item: Int, name: String, price: Double = 0.00) {
        if(cartItems.containsKey(item)) {
            var count: Int = cartItems[item]?.get(1)?.toInt() ?: 0
            count++
            cartItems[item]?.set(1, count.toString())
        } else {
            cartItems[item] = arrayOf(name, "1", price.toString())
        }

        changeTotal(cartItems[item]?.get(2)?.toDouble() ?: 0.00, "add")
    }

    fun subtractFromCart(item: Int) {
        val count = cartItems[item]?.get(1)?.toInt() ?: 0
        if(count > 1) {
            var count: Int = cartItems[item]?.get(1)?.toInt() ?: 0
            count--
            cartItems[item]?.set(1, count.toString())
            changeTotal(cartItems[item]?.get(2)?.toDouble() ?: 0.00, "subtract")
        } else {
            deleteFromCart(item)
        }


    }

    fun deleteFromCart(item: Int) {

        val price = cartItems[item]?.get(2)?.toDouble() ?: 0.00
        val count = cartItems[item]?.get(1)?.toInt() ?: 0
        val amountChange = price * count
        changeTotal(amountChange, "subtract")

        cartItems.remove(item)
    }

    fun getCart(): Map<Int, Array<String>>{
        return cartItems
    }

    fun checkout() {
        val checkoutOrder = Order(
            date = DateUtils.getCurrentDate(),
            items = cartItems.toMap(),
            total = formatTotal()
        )

        addOrder(checkoutOrder)
    }

    fun formatTotal(): String {
        return "%.2f".format(cartTotal)
    }
}