package com.example.themovie.view.home

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import com.bumptech.glide.load.engine.Resource
import com.example.core.domain.model.Movies
import com.example.core.domain.usecase.MoviesUseCase

class HomeViewModel @ViewModelInject constructor(
    private val moviesUseCase: MoviesUseCase,
    @Assisted state: SavedStateHandle
) :
    ViewModel() {
    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val movies = currentQuery.switchMap { queryString ->
        moviesUseCase.getAllMovies(queryString).asLiveData()
    }

    val genres = moviesUseCase.getAllGenre().asLiveData()

    fun setMoviesGenre(genre: String) {
        currentQuery.value = genre
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = ""
    }
}