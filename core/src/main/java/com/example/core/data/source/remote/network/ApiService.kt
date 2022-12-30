package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.ListGenreResponse
import com.example.core.data.source.remote.response.ListMoviesResponse
import com.example.core.data.source.remote.response.ListReviewResponse
import com.example.core.data.source.remote.response.ListVideoResponse
import com.example.core.utils.key
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/{category}")
    suspend fun getMovie(
        @Path("category") category: String = "popular",
        @Query("with_genres") withGenres: String = "",
        @Query("api_key") api_key: String = key,
        @Query("page") page: Int
    ): ListMoviesResponse

    @GET("genre/movie/list")
    suspend fun getGenre(
        @Query("api_key") api_key: String = key
    ): ListGenreResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getReview(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String = key
        ): ListReviewResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getVideo(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String = key
    ): ListVideoResponse
}