package com.example.mozio.pizza.repository

import com.example.mozio.pizza.repository.PizzaRepository
import com.example.mozio.pizza.repository.PizzaRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class PizzaRepositoryModule {

    @Binds
    abstract fun bindPizzaRepository(impl: PizzaRepositoryImpl): PizzaRepository

}