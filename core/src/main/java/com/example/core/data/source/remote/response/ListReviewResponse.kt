package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListReviewResponse (
    @field:SerializedName("results")
    val review: List<ReviewResponse>
)