package com.example.core.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.core.BuildConfig
import com.example.core.R
import java.util.*

const val key = BuildConfig.MOVIES_ACCESS_KEY
const val APIMOVIE = "https://api.themoviedb.org/3/"
const val YOUTUBEURL = " https://www.youtube.com/watch?v="

fun getProgressDrawable(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}
fun ImageView.loadImage(uri:String?, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_baseline_error_outline_24)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

fun loadImage(view: ImageView, url:String?){
    view.loadImage("https://image.tmdb.org/t/p/w154"+url, getProgressDrawable(view.context))
}
fun loadImageFull(view: ImageView, url:String?){
    view.loadImage("https://image.tmdb.org/t/p/original"+url, getProgressDrawable(view.context))
}
val isRTL: Boolean
    get() = isRTL(Locale.getDefault())

private fun isRTL(locale: Locale): Boolean {
    val directionality = Character.getDirectionality(locale.displayName[0]).toInt()
    return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT.toInt() || directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC.toInt()
}