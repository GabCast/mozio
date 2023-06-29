package com.example.mozio.networking

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HttpClientModule {

    @Provides
    @Singleton
    fun provideRetrofitClient(baseUrl: String): Retrofit {
        return MozioHttpClient(baseUrl).getClient()
    }
}
