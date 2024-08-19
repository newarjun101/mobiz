package com.kbz.mobiz.presentation.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kbz.mobiz.R
import com.kbz.mobiz.core.arguments.DetailArgument
import com.kbz.mobiz.core.deligation.RecentClickDeligation
import com.kbz.mobiz.core.deligation.SearchClick
import com.kbz.mobiz.core.extension.hideKeyboard
import com.kbz.mobiz.core.services.apiService.ApiResponse
import com.kbz.mobiz.databinding.ActivitySearchBinding
import com.kbz.mobiz.domain.data.vos.MovieVo
import com.kbz.mobiz.domain.data.vos.RecentVo
import com.kbz.mobiz.domain.data.vos.SearchVo
import com.kbz.mobiz.presentation.adapters.RecentKeywordRecyclerAdapter
import com.kbz.mobiz.presentation.adapters.SearchRecyclerAdapter
import com.kbz.mobiz.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchActivity : AppCompatActivity(),SearchClick,RecentClickDeligation {
    private  val   searchBinding : ActivitySearchBinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    private var isSearchClick = false
    private lateinit var  movieRecyclerView : RecyclerView
    private lateinit var  recentRecyclerView : RecyclerView
    private lateinit var   adapter : SearchRecyclerAdapter
    private lateinit var   recentAdapter : RecentKeywordRecyclerAdapter
    private lateinit var searchTextField : EditText
    private  val searchViewModel : SearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  enableEdgeToEdge()
        window.attributes.windowAnimations = R.anim.fade_in
        setContentView(searchBinding.root)
     /*   ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        movieRecyclerView = searchBinding.searchMovieRecyclerView
        recentRecyclerView = searchBinding.recentRecyclerView
        adapter  =  SearchRecyclerAdapter(this)
        recentAdapter  =  RecentKeywordRecyclerAdapter(this)
        movieRecyclerView.layoutManager = LinearLayoutManager(this)
        recentRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        movieRecyclerView.adapter = adapter
        recentRecyclerView.adapter = recentAdapter
        val titleView = searchBinding.searchIncludeCustomAppBar.homeAppBarTitleTextView
        searchTextField = searchBinding.searchIncludeCustomAppBar.homeSerchField
        searchTextField.isFocusableInTouchMode = true
        searchTextField.requestFocus()
        onSearch()
        val paint = titleView.paint
        val width = paint.measureText(titleView.text.toString())
        val textShader: Shader = LinearGradient(0f, 0f, width, titleView.textSize, intArrayOf(
            Color.parseColor("#58D4FE"),
            // Color.parseColor("#4CAF50"),
            Color.parseColor("#CB55FD")
        ), null, Shader.TileMode.REPEAT)
        titleView.paint.setShader(textShader)
        getMovieResult()
        getRecentFromRoom()
        if(!isSearchClick) {
            getMovieFromRoom()
        }
        searchBinding.progressBar.visibility = View.GONE
    }


    private fun onSearch() {
        searchTextField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if(searchTextField.text.toString().trim().isNotEmpty()){
                    searchViewModel.getMovieFromApi(searchTextField.text.toString())
                    searchBinding.progressBar.visibility = View.VISIBLE
                    isSearchClick = true
                }else {
                    Toast.makeText(this,"Seach field can't be empty",Toast.LENGTH_SHORT).show()
                }
                searchTextField.hideKeyboard()
            }
            true
        }
    }


    @SuppressLint("SetTextI18n")
    private  fun getMovieResult() {
        lifecycleScope.launch(Dispatchers.Main) {
            searchViewModel.getMovieListFromApi.collect{ value ->
                searchBinding.searchErrorLayoutId.errorCard.visibility = View.GONE
                searchBinding.progressBar.visibility = View.GONE
                searchBinding.searchEmptyTextView.text = ""
                when (value) {
                    is ApiResponse.Loading -> {
                       if(isSearchClick){
                           searchBinding.progressBar.visibility = View.VISIBLE
                       }
                    }
                    is ApiResponse.Success -> {
                        searchBinding.progressBar.visibility = View.GONE
                        Log.d("ArjunSearchResult", " arjunnnn ${value.data}")
                        value.data?.let {data ->
                            Log.d("ArjunSearchResult", " arjun ${data}")
                            adapter.setMovies(data)
                            if(data.isEmpty()){
                              searchBinding.searchEmptyTextView.text = "No data found"
                            }
                        }
                    }

                    else -> {
                        searchBinding.progressBar.visibility = View.GONE
                        //     searchBinding.homeErrorLayoutId.errorCard.visibility = View.VISIBLE
                        searchBinding.searchErrorLayoutId.errorCard.visibility = View.VISIBLE
                        searchBinding.searchErrorLayoutId.errorTextView.text = "${value.message}"
                        searchBinding.searchErrorLayoutId.tryAgain.setOnClickListener {
                         //   onRetry()
                        }


                    }
                }

            }
        }
    }

    private  fun getMovieFromRoom() {
        lifecycleScope.launch(Dispatchers.Main) {
            searchBinding.progressBar.visibility = View.GONE
            searchBinding.searchErrorLayoutId.errorCard.visibility = View.GONE
            searchViewModel.getMovieList.collect{
                if(!isSearchClick ) {
                    adapter.setMovies(it)
                }
            }
        }
    }

    private  fun getRecentFromRoom() {
        lifecycleScope.launch(Dispatchers.Main) {
            searchViewModel.getRecentKeywordList.collect{
                    recentAdapter.setMovies(it)
            }
        }
    }

    override fun onClickMovie(vo: SearchVo) {
        Log.d("Arjun","id==>${vo.id}")
        DetailArgument.onClickDetail(MovieVo(id = vo.id, posterPath = vo.posterPath,title = vo.title, voteAverage = vo.voteAverage))
        val intent = Intent(this,MovieDetailActivity::class.java)
        startActivity(intent)
    }

    override fun onClickRecent(vo: RecentVo) {
        isSearchClick = true
        searchTextField.setText(vo.title)
        searchViewModel.getMovieFromApi(vo.title)
        searchBinding.progressBar.visibility = View.VISIBLE
        searchTextField.hideKeyboard()
    }

    override fun onDeleteRecent(vo: RecentVo) {
        searchViewModel.deleteReent(vo)
    }


}