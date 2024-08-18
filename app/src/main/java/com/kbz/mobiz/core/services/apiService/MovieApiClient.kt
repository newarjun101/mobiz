package com.kbz.mobiz.core.services.apiService

import com.kbz.mobiz.domain.vos.MovieDetailVo
import com.kbz.mobiz.domain.vos.MovieResponseVo
import com.kbz.mobiz.domain.vos.TrailerResponseVo
import com.kbz.mobiz.domain.vos.TrailerVo
import com.kbz.mobiz.utils.ApiConstant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiClient {

    @GET(ApiConstant.popularMovie)
    suspend fun getPopularMovie() : Response<MovieResponseVo>

    @GET("3/movie/{id}?api_key=e18c2a3110a5a4d2cca1727c6e4c3b8e")
    suspend fun getMovieDetail(@Path("id") id: String) : Response<MovieDetailVo>

    @GET("3/movie/{id}/videos?api_key=e18c2a3110a5a4d2cca1727c6e4c3b8e")
    suspend fun getTrailerVideo(@Path("id") id: String) : Response<TrailerResponseVo>
}