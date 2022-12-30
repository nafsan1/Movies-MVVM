package com.example.core.domain.model

data class Genre(
    val id: Int,
    val name: String,
    var genres: Boolean = false
)