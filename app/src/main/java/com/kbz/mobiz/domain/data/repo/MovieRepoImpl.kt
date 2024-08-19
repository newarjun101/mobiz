package com.kbz.mobiz.domain.data.repo

import android.annotation.SuppressLint
import com.kbz.mobiz.core.services.apiService.ApiResponse
import com.kbz.mobiz.core.services.apiService.MovieApiClient
import com.kbz.mobiz.domain.data.daos.MovieDao
import com.kbz.mobiz.domain.data.daos.RecentDao
import com.kbz.mobiz.domain.data.daos.SearchMovieDao
import com.kbz.mobiz.domain.data.repo.abstacts.MovieRepo
import com.kbz.mobiz.domain.data.vos.MovieDetailVo
import com.kbz.mobiz.domain.data.vos.MovieVo
import com.kbz.mobiz.domain.data.vos.RecentVo
import com.kbz.mobiz.domain.data.vos.SearchVo
import com.kbz.mobiz.domain.data.vos.TrailerVo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepoImpl @Inject constructor (private  val apiClient: MovieApiClient, private  val movieDao: MovieDao, private  val searchMovieDao: SearchMovieDao, private  val recentDao: RecentDao) :
    MovieRepo {
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

    @SuppressLint("SuspiciousIndentation")
    override suspend fun getSearchMovieFromApi(searchValue : String): Flow<ApiResponse<List<SearchVo>?>> =   flow<ApiResponse<List<SearchVo>?>> {
        emit(ApiResponse.Loading())
        try {
            val result = apiClient.getSearchMovie(name = searchValue)
            if(result.isSuccessful) {
                recentDao.addRecent(RecentVo(title = searchValue))
                val movieList : MutableList<SearchVo> = mutableListOf()
                    result.body()?.results?.map {value ->
                        movieList.add(value.mapper())
                }
                emit(ApiResponse.Success(message = "Success", data = movieList))
                searchMovieDao.addSearchMovieWithList(movieList)
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
    override suspend fun getAllRecentKeywords(): Flow<List<RecentVo>> = recentDao.getAllRecentKeywords()
    override suspend fun deleteRecent(vo: RecentVo) = recentDao.deleteRecent(vo)

    override suspend fun getSearchMovieFromRoom(): Flow<List<SearchVo>> = searchMovieDao.getAllMovie()


}