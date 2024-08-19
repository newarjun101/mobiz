package com.kbz.mobiz.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kbz.mobiz.core.services.apiService.ApiResponse
import com.kbz.mobiz.domain.data.repo.MovieRepoImpl
import com.kbz.mobiz.domain.data.vos.MovieVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private  val movieRepoImpl: MovieRepoImpl)  : ViewModel(){

    private val movieApiResult = MutableStateFlow<ApiResponse<List<MovieVo>?>>(ApiResponse.Loading())
    val getMovieListFromApi: StateFlow<ApiResponse<List<MovieVo>?>> get() = movieApiResult
    private val _movieList = MutableStateFlow<List<MovieVo>>(mutableListOf())
    val getMovieList: StateFlow<List<MovieVo>> get() = _movieList
    init {
        Log.d("Arjun","Working")
        getMovieFromApi()
       getMovieFromRoom()
    }
    @SuppressLint("SuspiciousIndentation")
     fun getMovieFromApi() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepoImpl.getMovieFromApi().collect {
              movieApiResult.value = it
                it.data?.let {movie ->
                    _movieList.value = movie
                }

            }
        }
    }

     fun getMovieFromRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepoImpl.getMovieFromRoom().collect {
            //  rateApiResult.value = it
                _movieList.value = it
                Log.d("ArjunDB", "room database ${it}")
                Log.d("ArjunDB", "room database ${it.size}")
            }
        }
    }



}