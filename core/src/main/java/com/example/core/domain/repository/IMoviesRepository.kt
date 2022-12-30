package com.example.core.domain.repository

import androidx.paging.PagingData
import com.example.core.data.Resource
import com.example.core.data.source.remote.response.MoviesResponse
import com.example.core.domain.model.Genre
import com.example.core.domain.model.Movies
import com.example.core.domain.model.Review
import com.example.core.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {

    fun getAllMovies(genre:String):Flow<PagingData<Movies>>

    fun getAllGenre():Flow<Resource<List<Genre>>>

    fun getReview(id:Int):Flow<Resource<List<Review>>>
    fun getVideo(id:Int):Flow<Resource<List<Video>>>

}