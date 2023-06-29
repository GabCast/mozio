package com.example.mozio.pizza.model

import retrofit2.Response
import retrofit2.http.GET

interface PizzaService {

    @GET("pizzas.json")
    suspend fun menu(
    ): Response<ArrayList<Menu>>
}
