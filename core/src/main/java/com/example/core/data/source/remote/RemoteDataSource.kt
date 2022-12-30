package com.example.core.data.source.remote

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.ApiService
import com.example.core.data.source.remote.response.GenreResponse
import com.example.core.data.source.remote.response.MoviesResponse
import com.example.core.data.source.remote.response.ReviewResponse
import com.example.core.data.source.remote.response.VideoResponse
import com.example.core.utils.MoviesPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

     fun getAllMovies(genre:String): Flow<PagingData<MoviesResponse>> {
         return Pager(
            PagingConfig(pageSize = 20, enablePlaceholders = false)
        ) {
             MoviesPagingSource(apiService,genre)
        }.flow
    }


    suspend fun getAllGenre():Flow<ApiResponse<List<GenreResponse>>>{
        return flow {
            try {
                val response = apiService.getGenre()
                val dataArray = response.genre
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.genre))
                }else {
                    emit(ApiResponse.Empty)
                }
            }catch (e:Exception){
            emit(ApiResponse.Error(e.toString()))
        }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getReview(id:Int):Flow<ApiResponse<List<ReviewResponse>>>{
        return flow {
            try {
                val response = apiService.getReview(id)
                val dataArray = response.review
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.review))
                }else {
                    emit(ApiResponse.Empty)
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getVideo(id:Int):Flow<ApiResponse<List<VideoResponse>>>{
        return flow {
            try {
                val response = apiService.getVideo(movie_id = id)
                val dataArray = response.video
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.video))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

}