package com.kbz.mobiz.domain.repo

import com.kbz.mobiz.core.services.apiService.ApiResponse
import com.kbz.mobiz.core.services.apiService.MovieApiClient
import com.kbz.mobiz.domain.daos.MovieDao
import com.kbz.mobiz.domain.repo.abstacts.MovieRepo
import com.kbz.mobiz.domain.vos.MovieDetailVo
import com.kbz.mobiz.domain.vos.MovieVo
import com.kbz.mobiz.domain.vos.TrailerVo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepoImpl @Inject constructor (private  val apiClient: MovieApiClient,private  val movieDao: MovieDao) : MovieRepo{
    override suspend fun getMovieFromApi(): Flow<ApiResponse<List<MovieVo>?>> = flow<ApiResponse<List<MovieVo>?>> {
        emit(ApiResponse.Loading())
       try {
           val result = apiClient.getPopularMovie()
           if(result.isSuccessful) {
               emit(ApiResponse.Success(message = "Success", data = result.body()?.results))
               result.body()?.results.let {data ->
                  movieDao.addMovieWithList(data!!)
               }
           } else {
               emit(ApiResponse.Failure(message = result.message()))
           }

       }
       catch (error : Exception) {
           emit(ApiResponse.Failure(message = "${error.message}"))
       }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMovieDetailFromApi(id: String): Flow<ApiResponse<MovieDetailVo?>> =  flow<ApiResponse<MovieDetailVo?>> {
        emit(ApiResponse.Loading())
        try {
            val result = apiClient.getMovieDetail(id = id)
            if(result.isSuccessful) {
                emit(ApiResponse.Success(message = "Success", data = result.body()))
            } else {
                emit(ApiResponse.Failure(message = result.message()))
            }
        }
        catch (error : Exception) {
            emit(ApiResponse.Failure(message = "${error.message}"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getTrailerVideo(id: String): Flow<ApiResponse<List<TrailerVo>?>> = flow<ApiResponse<List<TrailerVo>?>>{
        emit(ApiResponse.Loading())
        try {
            val result = apiClient.getTrailerVideo(id = id)
            if(result.isSuccessful) {
                emit(ApiResponse.Success(message = "Success", data = result.body()?.results))
            } else {
                emit(ApiResponse.Failure(message = result.message()))
            }
        }
        catch (error : Exception) {
            emit(ApiResponse.Failure(message = "${error.message}"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMovieFromRoom(): Flow<List<MovieVo>> =  movieDao.getAllMovie()


}