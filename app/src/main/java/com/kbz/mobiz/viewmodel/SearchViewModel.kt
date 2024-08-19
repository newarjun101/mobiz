package com.kbz.mobiz.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kbz.mobiz.core.services.apiService.ApiResponse
import com.kbz.mobiz.domain.data.repo.MovieRepoImpl
import com.kbz.mobiz.domain.data.vos.RecentVo
import com.kbz.mobiz.domain.data.vos.SearchVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private  val movieRepoImpl: MovieRepoImpl) : ViewModel() {
    private val movieApiResult = MutableStateFlow<ApiResponse<List<SearchVo>?>>(ApiResponse.Loading())
    val getMovieListFromApi: StateFlow<ApiResponse<List<SearchVo>?>> get() = movieApiResult
    private val _movieList = MutableStateFlow<List<SearchVo>>(mutableListOf())
    val getMovieList: StateFlow<List<SearchVo>> get() = _movieList
    private val _recentKeywordList = MutableStateFlow<List<RecentVo>>(mutableListOf())
    val getRecentKeywordList: StateFlow<List<RecentVo>> get() = _recentKeywordList

    init {
        getMovieFromRoom()
        getRecentKeywordFromRoom()
    }

    @SuppressLint("SuspiciousIndentation")
    fun getMovieFromApi(searchString : String) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepoImpl.getSearchMovieFromApi(searchValue = searchString).collect {
                movieApiResult.value = it
                Log.d("ArjunSearch", "Search api ==> ${it.data}")
                it.data?.let {movie ->
                    _movieList.value = movie
                }

            }
        }
    }

    private fun getMovieFromRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepoImpl.getSearchMovieFromRoom().collect {
                //  rateApiResult.value = it
                _movieList.value = it
                Log.d("ArjunDB", "room database ${it}")
                Log.d("ArjunDB", "room database ${it.size}")
            }
        }
    }
    private fun getRecentKeywordFromRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepoImpl.getAllRecentKeywords().collect {
                //  rateApiResult.value = it
                _recentKeywordList.value = it
                Log.d("ArjunRecentDB", "recentKeyword ${it}")
                Log.d("ArjunRecentDB", "recentKeyword ${it.size}")
            }
        }
    }

    fun deleteReent(vo : RecentVo) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepoImpl.deleteRecent(vo = vo)
        }
    }

}