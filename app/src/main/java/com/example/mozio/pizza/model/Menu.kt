package com.example.mozio.pizza.model

data class Menu(
    val name: String,
    val price: Float,
    var totalDisabled: Boolean = false,
    var halfDisabled: Boolean = false
)
