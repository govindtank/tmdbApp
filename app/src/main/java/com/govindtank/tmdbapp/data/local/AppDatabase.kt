package com.govindtank.tmdbapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.govindtank.tmdbapp.data.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}