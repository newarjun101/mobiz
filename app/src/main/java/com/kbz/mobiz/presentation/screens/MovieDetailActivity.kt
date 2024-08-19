package com.kbz.mobiz.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kbz.mobiz.R
import com.kbz.mobiz.core.services.apiService.ApiResponse
import com.kbz.mobiz.databinding.ActivityMovieDetailBinding
import com.kbz.mobiz.viewmodel.MovieDetailViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private val detailBinding : ActivityMovieDetailBinding by lazy {
        ActivityMovieDetailBinding.inflate(layoutInflater)
    }
    var id:  String? = null
    var title : String? = null
    private  val detailViewModel : MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.attributes.windowAnimations = R.anim.fade_in
        setContentView(detailBinding.root)
        getMovieResult()
        getYoutubeResult()
    }

    override fun onDestroy() {
        detailBinding.movieInfoInclude.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.pause()

            }
        })
        super.onDestroy()

    }

    @SuppressLint("SetTextI18n")
    private  fun getMovieResult() {
        lifecycleScope.launch(Dispatchers.Main) {
            detailViewModel.getMovieDetail.collect{ value ->

                Log.d("MovieDetail","MovieDetailResult =>> ${value.data}")
                detailBinding.detailErrorLayoutId.errorCard.visibility = View.GONE
                detailBinding.movieInfoInclude.root.visibility = View.GONE
                when (value) {
                    is ApiResponse.Loading -> {
                        detailBinding.detailProgressBar.visibility = View.VISIBLE
                    }
                    is ApiResponse.Success -> {
                        detailBinding.movieInfoInclude.root.visibility = View.VISIBLE
                        detailBinding.movieInfoInclude.movieTitleTextView.text  = value.data?.title
                        detailBinding.movieInfoInclude.releaseYearTextView.text  = "Release Date : ${value.data?.releaseDate}"
                        //detailBinding.movieInfoInclude.ratingTextView.text  = "Rating : ${value.data?.voteAverage.getOneDecimalValue()}"
                        detailBinding.movieInfoInclude.runtimeTextView.text  = "Duration : ${value.data?.runtime} mins"
                        detailBinding.movieInfoInclude.descriptionTextView.text  = "${value.data?.overview}"
                        detailBinding.detailProgressBar.visibility = View.GONE
                    }

                    else -> {
                        detailBinding.detailProgressBar.visibility = View.GONE
                        //     mainBinding.homeErrorLayoutId.errorCard.visibility = View.VISIBLE
                        detailBinding.detailErrorLayoutId.errorCard.visibility = View.VISIBLE
                        detailBinding.detailErrorLayoutId.errorTextView.text = "${value.message}"
                        detailBinding.detailErrorLayoutId.tryAgain.setOnClickListener {
                          //  onRetry()
                            detailViewModel.getMovieDetailFromApi()
                            detailViewModel.getTrailerVideo()
                        }


                    }
                }

            }
        }
    }

    private fun getYoutubeResult() {
        lifecycleScope.launch(Dispatchers.Main) {
            detailViewModel.getTrailerResult.collect{
               it.data?.let {value ->
                   if(value.isNotEmpty()) {
                       Log.d("MovieDetail","Youtube id => ${value[0].iso3166_1!!.value}")
                       detailBinding.movieInfoInclude.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                           override fun onReady(youTubePlayer: YouTubePlayer) {
                               val videoId = value[0].key!!
                               youTubePlayer.loadVideo(videoId, 0f)

                           }
                       })
                   }
               }
            }
        }
    }

}