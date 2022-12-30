package com.example.core.data

import androidx.paging.PagingData
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.response.GenreResponse
import com.example.core.data.source.remote.response.MoviesResponse
import com.example.core.data.source.remote.response.ReviewResponse
import com.example.core.data.source.remote.response.VideoResponse
import com.example.core.domain.model.Genre
import com.example.core.domain.model.Movies
import com.example.core.domain.model.Review
import com.example.core.domain.model.Video
import com.example.core.domain.repository.IMoviesRepository
import com.example.core.utils.AppExecutors
import com.example.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMoviesRepository {

    override fun getAllMovies(genre:String): Flow<PagingData<Movies>> {
        return remoteDataSource.getAllMovies(genre = genre).map {
            DataMapper.mapResponseToDomainPaging(it)
        }
    }

    override fun getAllGenre(): Flow<Resource<List<Genre>>> =
        object : NetworkBoundResource<List<Genre>, List<GenreResponse>>() {
            override fun loadFromDB(): Flow<List<Genre>> {
                return localDataSource.getAllGenre().map {
                    DataMapper.genreEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Genre>?): Boolean = true
                //data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<GenreResponse>>> =
                remoteDataSource.getAllGenre()

            override suspend fun saveCallResult(data: List<GenreResponse>) {
                val dataArray = DataMapper.genreResponsesToEntities(data)
                localDataSource.insertGenre(dataArray)
            }

        }.asFlow()

    override fun getReview(id:Int): Flow<Resource<List<Review>>> =
        object : NetworkBoundResource<List<Review>, List<ReviewResponse>>() {
            override fun loadFromDB(): Flow<List<Review>> {
                return localDataSource.getAllReview(id).map {
                    DataMapper.reviewEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Review>?): Boolean = true


            override suspend fun createCall(): Flow<ApiResponse<List<ReviewResponse>>> =
                remoteDataSource.getReview(id)

            override suspend fun saveCallResult(data: List<ReviewResponse>) {
                val dataArray = DataMapper.reviewResponsesToEntities(data,id)
                localDataSource.insertReview(dataArray)
            }

        }.asFlow()

    override fun getVideo(id: Int): Flow<Resource<List<Video>>>  =
        object : NetworkBoundResource<List<Video>, List<VideoResponse>>() {
            override fun loadFromDB(): Flow<List<Video>> {
                return localDataSource.getAllVideo(id).map {
                    DataMapper.videoEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Video>?): Boolean = // data == null || data.isEmpty()
                true // ganti dengan true jika ingin selalu mengambil data dari internet


            override suspend fun createCall(): Flow<ApiResponse<List<VideoResponse>>> =
                remoteDataSource.getVideo(id)

            override suspend fun saveCallResult(data: List<VideoResponse>) {
                val dataArray = DataMapper.videoResponsesToEntities(data,id)
                localDataSource.insertVideo(dataArray)
            }

        }.asFlow()


}