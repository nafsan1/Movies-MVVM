package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListVideoResponse (
    @field:SerializedName("results")
    val video: List<VideoResponse>
)