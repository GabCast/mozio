package com.example.mozio.pizza

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface PizzaService {

    @GET("pizzas")
    suspend fun menu(
    ): Response<ResponseBody>
}
