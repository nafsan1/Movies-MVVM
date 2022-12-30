package com.example.themovie.view.detail

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.core.domain.usecase.MoviesUseCase
import com.example.themovie.view.home.HomeViewModel

class DetailViewModel @ViewModelInject constructor(
    private val moviesUseCase: MoviesUseCase,
    @Assisted state: SavedStateHandle
):ViewModel() {

    private val currentId = state.getLiveData(
        CURRENT_ID_REVIEW,
        DEFAULT_ID_REVIEW
    )

    private val videoId = MutableLiveData(DEFAULT_VIDEO_REVIEW)



    val review = currentId.switchMap { id ->

        moviesUseCase.getReview(id.toInt()).asLiveData()
    }

    val video = videoId.switchMap { id ->
        moviesUseCase.getVideo(id.toInt()).asLiveData()
    }

    fun setVideo(id:Int){
        videoId.value = id.toString()
    }
    fun setReview(id:Int){
        currentId.value = id.toString()
    }
    companion object {
        private const val CURRENT_ID_REVIEW = "0101"
        private const val DEFAULT_ID_REVIEW = "0"

        private const val DEFAULT_VIDEO_REVIEW = "01"
    }
}