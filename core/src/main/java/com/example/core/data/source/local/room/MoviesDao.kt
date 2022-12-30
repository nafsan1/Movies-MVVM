package com.example.core.data.source.local.room

import androidx.room.*
import com.example.core.data.source.local.entity.GenreEntity
import com.example.core.data.source.local.entity.MoviesEntity
import com.example.core.data.source.local.entity.ReviewEntity
import com.example.core.data.source.local.entity.VideosEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {

    @Query("SELECT * FROM genres")
    fun getAllGenre(): Flow<List<GenreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(genre: List<GenreEntity>)

    @Query("SELECT * FROM review WHERE id_movies = :idMovies")
    fun getAllReview(idMovies:Int): Flow<List<ReviewEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: List<ReviewEntity>)

    @Query("SELECT * FROM video WHERE id_movies = :idMovies ")
    fun getAllVideo(idMovies:Int): Flow<List<VideosEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideo(video: List<VideosEntity>)
}