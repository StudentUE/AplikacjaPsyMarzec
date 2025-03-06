package com.example.psymarzecv2.dane.lokalne.bazadanych

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DogEntity::class],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dogDao(): DogEntityDao
}
