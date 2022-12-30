package com.example.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.data.source.local.entity.GenreEntity
import com.example.core.data.source.local.entity.MoviesEntity
import com.example.core.data.source.local.entity.ReviewEntity
import com.example.core.data.source.local.entity.VideosEntity


@Database(entities = [MoviesEntity::class,GenreEntity::class, ReviewEntity::class,VideosEntity::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}