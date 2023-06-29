package com.example.mozio.pizza.repository

import com.example.mozio.pizza.model.Menu
import com.example.mozio.pizza.model.PizzaService
import retrofit2.Retrofit
import javax.inject.Inject

class PizzaRepositoryImpl @Inject constructor(retrofitClient: Retrofit) : PizzaRepository {

    private val client = retrofitClient.create(PizzaService::class.java)

    override suspend fun menu():ArrayList<Menu>? {
        val response = client.menu()
        return if (response.isSuccessful) {
            response.body()
        } else {
            // this should be handle by an error handle
            arrayListOf()
        }

    }
}
