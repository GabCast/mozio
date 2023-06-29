package com.example.mozio.stamps

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class StampsRepositoryModule {

    @Binds
    abstract fun bindStampsRepository(impl: StampsRepositoryImpl): StampsRepository
}
