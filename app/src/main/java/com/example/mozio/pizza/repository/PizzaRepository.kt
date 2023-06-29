package com.example.mozio.pizza.repository

import com.example.mozio.pizza.model.Menu

interface PizzaRepository {

    suspend fun menu(): ArrayList<Menu>?
}