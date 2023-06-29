package com.example.mozio.pizza

interface PizzaRepository {

    suspend fun menu(): ArrayList<Pizza>?
}