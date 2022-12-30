package com.example.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review")
data class ReviewEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "id_movies")
    val id_movies: Int


)