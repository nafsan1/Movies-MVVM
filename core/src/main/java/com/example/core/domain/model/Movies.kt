package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Movies (
    val id: Int,
    val title: String,
    var release_date: String? = "",
    var poster_path: String? = "",
    val overview: String,
    val isFavorite:Boolean
): Parcelable