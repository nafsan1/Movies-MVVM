package com.example.core.utils

import androidx.paging.PagingData
import androidx.paging.map
import com.example.core.data.source.local.entity.GenreEntity
import com.example.core.data.source.local.entity.MoviesEntity
import com.example.core.data.source.local.entity.ReviewEntity
import com.example.core.data.source.local.entity.VideosEntity
import com.example.core.data.source.remote.response.GenreResponse
import com.example.core.data.source.remote.response.MoviesResponse
import com.example.core.data.source.remote.response.ReviewResponse
import com.example.core.data.source.remote.response.VideoResponse
import com.example.core.domain.model.Genre
import com.example.core.domain.model.Movies
import com.example.core.domain.model.Review
import com.example.core.domain.model.Video

object DataMapper {


    fun mapEntitiesToDomain(input: List<MoviesEntity>): List<Movies> =
        input.map {
            Movies(
                id = it.id,
                title = it.title,
                release_date = it.release_date,
                poster_path = it.poster_path,
                overview = it.overview,
                isFavorite = it.isFavorite
            )
        }



    fun mapResponseToDomainPaging(input: PagingData<MoviesResponse>): PagingData<Movies> =
        input.map {

            Movies(
                id = it.id,
                title = it.title,
                release_date = it.release_date,
                poster_path = it.poster_path,
                overview = it.overview,
                isFavorite = false
            )
        }

    fun genreResponsesToEntities(input: List<GenreResponse>): List<GenreEntity> {
        val list = ArrayList<GenreEntity>()
        input.map {
            val genre = GenreEntity(
                id = it.id,
                name = it.name
            )
            list.add(genre)
        }
        return list
    }

    fun genreEntitiesToDomain(input: List<GenreEntity>): List<Genre> =
        input.map {
            Genre(
                id = it.id,
                name = it.name
            )
        }

    fun reviewResponsesToEntities(input: List<ReviewResponse>,idMovie:Int): List<ReviewEntity> {
        val list = ArrayList<ReviewEntity>()
        input.map {
            val review = ReviewEntity(
                id = it.id,
                author = it.author,
                content = it.content,
                url = it.url,
                id_movies = idMovie
            )
            list.add(review)
        }
        return list
    }

    fun reviewEntitiesToDomain(input: List<ReviewEntity>): List<Review> =
        input.map {
            Review(
                id = it.id,
                author = it.author,
                content = it.content,
                url = it.url
            )
        }

    fun videoResponsesToEntities(input: List<VideoResponse>,idMovie:Int): List<VideosEntity> {
        val list = ArrayList<VideosEntity>()
        input.map {
            val video = VideosEntity(
                id = it.id,
                id_movies = idMovie,
                key = it.key
            )
            list.add(video)
        }
        return list
    }

    fun videoEntitiesToDomain(input: List<VideosEntity>): List<Video> =
        input.map {
            Video(
                id = it.id,
                key = it.key
            )
        }
}