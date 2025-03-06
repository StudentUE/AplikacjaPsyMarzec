
package com.example.psymarzecv2.dane.di

import com.example.psymarzecv2.dane.DefaultDogRepository
import com.example.psymarzecv2.dane.DogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsDogRepository(
        dogRepository: DefaultDogRepository
    ): DogRepository
}
