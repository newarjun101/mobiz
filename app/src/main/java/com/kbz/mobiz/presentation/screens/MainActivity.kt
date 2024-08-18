package com.kbz.mobiz.presentation.screens

import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kbz.mobiz.core.arguments.DetailArgument
import com.kbz.mobiz.core.deligation.OnMovieClick
import com.kbz.mobiz.core.services.apiService.ApiResponse
import com.kbz.mobiz.databinding.ActivityMainBinding
import com.kbz.mobiz.domain.vos.MovieVo
import com.kbz.mobiz.presentation.adapters.MovieRecyclerAdapter
import com.kbz.mobiz.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity(),OnMovieClick {
    private  val   mainBinding : ActivityMainBinding by lazy {
     ActivityMainBinding.inflate(layoutInflater)
    }

    lateinit var  movieRecyclerView : RecyclerView
    lateinit var   adapter : MovieRecyclerAdapter
  private  val homeViewModel : HomeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)
        val titleView = mainBinding.homeIncludeCustomAppBar.homeAppBarTitleTextView
        val paint = titleView.paint
        val width = paint.measureText(titleView.text.toString())
        val textShader: Shader = LinearGradient(0f, 0f, width, titleView.textSize, intArrayOf(
            Color.parseColor("#58D4FE"),
           // Color.parseColor("#4CAF50"),
            Color.parseColor("#CB55FD")
        ), null, Shader.TileMode.REPEAT)
        titleView.paint.setShader(textShader)
        mainBinding.homeIncludeCustomAppBar.homeSerchField.setOnClickListener {

            startActivity(Intent(this,SearchActivity::class.java))
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        movieRecyclerView = mainBinding.homeMovieRecyclerView
        adapter  =  MovieRecyclerAdapter(this)
        movieRecyclerView.layoutManager = LinearLayoutManager(this)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        movieRecyclerView.adapter = adapter
        getMovieResult()
        getMovieFromRoom()

    }

    private  fun getMovieResult() {
        lifecycleScope.launch(Dispatchers.Main) {
            homeViewModel.getMovieListFromApi.collect{ value ->
                mainBinding.homeErrorLayoutId.errorCard.visibility = View.GONE
                when (value) {
                    is ApiResponse.Loading -> {
                        mainBinding.progressBar.visibility = View.VISIBLE
                    }
                    is ApiResponse.Success -> {
                        mainBinding.progressBar.visibility = View.GONE
                        value.data.let {data ->
                            adapter.setMovies(data!!)
                        }
                    }

                    else -> {
                        mainBinding.progressBar.visibility = View.GONE
                        //     mainBinding.homeErrorLayoutId.errorCard.visibility = View.VISIBLE
                        mainBinding.homeErrorLayoutId.errorCard.visibility = View.VISIBLE
                        mainBinding.homeErrorLayoutId.errorTextView.text = "${value.message}"
                        mainBinding.homeErrorLayoutId.tryAgain.setOnClickListener {
                            onRetry()
                        }


                    }
                }

            }
        }
    }

    private  fun getMovieFromRoom() {
        lifecycleScope.launch(Dispatchers.Main) {
            homeViewModel.getMovieList.collect{
                adapter.setMovies(it)
            }
        }
    }
    override fun onClickMovie(vo: MovieVo) {

        Log.d("Arjun","id==>${vo.id}")
        DetailArgument.onClickDetail(vo)
        val intent = Intent(this,MovieDetailActivity::class.java)
        startActivity(intent)
    }
    private fun onRetry() {
        mainBinding.progressBar.visibility = View.VISIBLE
        homeViewModel.getMovieFromApi()
    }
}