package com.example.mozio

import com.example.mozio.networking.MozioHttpClient
import com.example.mozio.pizza.model.PizzaService
import io.mockk.every
import io.mockk.mockk
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpNetworkingMock {

    lateinit var client: MozioHttpClient

    fun mock() {
        val okHttpClient = OkHttpClient.Builder()
            .build()
        val httpClient = Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://static.mozio.com/mobile/tests/")
            .build()

        client= mockk()

        every { client.getClient() } answers { httpClient }

        every { client.getClient().create(PizzaService::class.java) } answers {
            httpClient.create(PizzaService::class.java)
        }
    }
}
