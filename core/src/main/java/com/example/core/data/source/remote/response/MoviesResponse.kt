package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

class MoviesResponse (

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("release_date")
    var release_date: String? = "",

    @field:SerializedName("poster_path")
    var poster_path: String? = "",

    @field:SerializedName("overview")
    val overview: String
)