package com.kbz.mobiz.domain.repo.abstacts

import com.kbz.mobiz.core.services.apiService.ApiResponse
import com.kbz.mobiz.domain.vos.MovieDetailVo
import com.kbz.mobiz.domain.vos.MovieVo
import com.kbz.mobiz.domain.vos.RecentVo
import com.kbz.mobiz.domain.vos.SearchVo
import com.kbz.mobiz.domain.vos.TrailerResponseVo
import com.kbz.mobiz.domain.vos.TrailerVo
import kotlinx.coroutines.flow.Flow

interface  MovieRepo {

    suspend fun getMovieFromApi() : Flow<ApiResponse<List<MovieVo>?>>
    suspend fun getSearchMovieFromApi(searchValue : String) : Flow<ApiResponse<List<SearchVo>?>>
    suspend fun getMovieDetailFromApi(id : String) : Flow<ApiResponse<MovieDetailVo?>>
    suspend fun getTrailerVideo(id : String) : Flow<ApiResponse<List<TrailerVo>?>>
    suspend fun getMovieFromRoom() : Flow<List<MovieVo>>
    suspend fun getAllRecentKeywords() : Flow<List<RecentVo>>
    suspend fun deleteRecent(vo: RecentVo)
    suspend fun getSearchMovieFromRoom() : Flow<List<SearchVo>>

}