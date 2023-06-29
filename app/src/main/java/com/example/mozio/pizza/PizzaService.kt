package com.example.mozio.pizza

import retrofit2.Response
import retrofit2.http.GET

interface PizzaService {

    @GET("pizzas")
    suspend fun menu(
    ): Response<ArrayList<Pizza>>
}
