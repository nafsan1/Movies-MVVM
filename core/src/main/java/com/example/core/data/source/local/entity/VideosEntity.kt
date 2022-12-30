package com.example.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video")
data class VideosEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "id_movies")
    val id_movies: Int,
    @ColumnInfo(name = "key")
    val key: String
)