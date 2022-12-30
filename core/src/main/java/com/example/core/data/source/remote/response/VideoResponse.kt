package com.example.core.data.source.remote.response

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class VideoResponse (
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("key")
    val key: String
)