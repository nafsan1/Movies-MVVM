package com.example.core.utils


import androidx.paging.PagingSource
import com.example.core.data.source.remote.network.ApiService
import com.example.core.data.source.remote.response.MoviesResponse
import retrofit2.HttpException
import java.io.IOException


private const val MOVIES_STARTING_PAGE_INDEX = 1

class MoviesPagingSource(
    private val apiService: ApiService,
    private val genre: String
) : PagingSource<Int, MoviesResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesResponse> {
        val position = params.key ?: MOVIES_STARTING_PAGE_INDEX

        return try {
            val response = apiService.getMovie(withGenres = genre,page = position)
            val movies = response.movie

            LoadResult.Page(
                data = movies,
                prevKey = if (position == MOVIES_STARTING_PAGE_INDEX) null else position -1,
                nextKey = if (movies.isEmpty()) null else position +1
            )
        }catch (exception: IOException) {
            LoadResult.Error(exception)

        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }
}