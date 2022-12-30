package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListGenreResponse(
    @field:SerializedName("genres")
    val genre: List<GenreResponse>
)