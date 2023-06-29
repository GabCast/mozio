package com.example.mozio.networking

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.jvm.Throws

class MozioHttpClient @Inject constructor(private val baseUrl: String) {

    private var httpClient: Retrofit? = null

    @Throws(IllegalStateException::class)
    fun getClient(): Retrofit {
        if (httpClient == null) buildClient()
        return httpClient?.let { it } ?: run { throw IllegalStateException() }
    }

    private fun buildClient() {
        val okHttpClient = with(OkHttpClient.Builder()) {
            addNetworkInterceptor(StethoInterceptor())
            connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            build()
        }
        httpClient = Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    companion object {
        const val CONNECTION_TIME_OUT = 30L
    }
}
