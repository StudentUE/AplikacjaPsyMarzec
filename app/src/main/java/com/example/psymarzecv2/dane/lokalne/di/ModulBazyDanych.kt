package com.example.psymarzecv2.dane.lokalne.di

import android.content.Context
import androidx.room.Room
import com.example.psymarzecv2.dane.lokalne.bazadanych.AppDatabase
import com.example.psymarzecv2.dane.lokalne.bazadanych.DogEntityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideDogDao(appDatabase: AppDatabase): DogEntityDao {
        return appDatabase.dogDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "DogsDB"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
