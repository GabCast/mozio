package com.example.mozio.pizza

import retrofit2.Retrofit
import javax.inject.Inject

class PizzaRepositoryImpl @Inject constructor(retrofitClient: Retrofit) : PizzaRepository {

    private val client = retrofitClient.create(PizzaService::class.java)

    override suspend fun menu():ArrayList<Pizza>? {
        val response = client.menu()
        return if (response.isSuccessful) {
            response.body()
        } else {
            // this should be handle by an error handle
            arrayListOf()
        }

    }
}
