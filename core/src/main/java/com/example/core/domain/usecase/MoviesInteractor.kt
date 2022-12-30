package com.example.core.domain.usecase

import androidx.paging.PagingData
import com.example.core.data.Resource
import com.example.core.domain.model.Genre
import com.example.core.domain.model.Movies
import com.example.core.domain.model.Review
import com.example.core.domain.model.Video
import com.example.core.domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesInteractor @Inject constructor(private val moviesRepository: IMoviesRepository) :
    MoviesUseCase {


    override fun getAllMovies(genre:String): Flow<PagingData<Movies>> = moviesRepository.getAllMovies(genre)
    override fun getAllGenre():Flow<Resource<List<Genre>>> = moviesRepository.getAllGenre()
    override fun getReview(id: Int): Flow<Resource<List<Review>>>  = moviesRepository.getReview(id = id)
    override fun getVideo(id: Int): Flow<Resource<List<Video>>> = moviesRepository.getVideo(id = id)


}