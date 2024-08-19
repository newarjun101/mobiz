package com.kbz.mobiz.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kbz.mobiz.core.arguments.DetailArgument
import com.kbz.mobiz.core.services.apiService.ApiResponse
import com.kbz.mobiz.domain.data.repo.MovieRepoImpl
import com.kbz.mobiz.domain.data.vos.MovieDetailVo
import com.kbz.mobiz.domain.data.vos.TrailerVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private  val movieRepoImpl: MovieRepoImpl) : ViewModel() {

    private val movieApiResult = MutableStateFlow<ApiResponse<MovieDetailVo?>>(ApiResponse.Loading())
    val getMovieDetail: StateFlow<ApiResponse<MovieDetailVo?>>  get() = movieApiResult
    private val trailerApiResult = MutableStateFlow<ApiResponse<List<TrailerVo>?>>(ApiResponse.Loading())
    val getTrailerResult: StateFlow<ApiResponse<List<TrailerVo>?>> get() = trailerApiResult
    init {
        Log.d("Arjun","Working")
        getMovieDetailFromApi()
        getTrailerVideo()

    }
    @SuppressLint("SuspiciousIndentation")
    fun getMovieDetailFromApi() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepoImpl.getMovieDetailFromApi(id = "${DetailArgument.movieVo?.id}").collect {
                movieApiResult.value = it
            }
        }
    }
    @SuppressLint("SuspiciousIndentation")
    fun getTrailerVideo() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepoImpl.getTrailerVideo(id = "${DetailArgument.movieVo?.id}").collect {
                trailerApiResult.value = it
            }
        }
    }
}