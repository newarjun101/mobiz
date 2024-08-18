package com.kbz.mobiz.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kbz.mobiz.R
import com.kbz.mobiz.core.deligation.OnMovieClick
import com.kbz.mobiz.core.extension.getOneDecimalValue
import com.kbz.mobiz.databinding.MovieCardBinding
import com.kbz.mobiz.domain.vos.MovieVo


 class MovieRecyclerAdapter(private  val itemClickListener: OnMovieClick) : RecyclerView.Adapter<MovieRecyclerViewHolder>() {

    private lateinit var  context : Context
    private var mMovieList: MutableList<MovieVo> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRecyclerViewHolder {

        context = parent.context
        val binding = MovieCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  mMovieList.size
    }

    override fun onBindViewHolder(holder: MovieRecyclerViewHolder, position: Int) {
        holder.onItemBind(mMovieList[position],context)

        holder.binding.movieCardCard.setOnClickListener{
            itemClickListener.onClickMovie(mMovieList[position])
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setMovies(movies : List<MovieVo>) {
        mMovieList = movies as ArrayList<MovieVo>
        notifyDataSetChanged()
    }

}

  class MovieRecyclerViewHolder(private  val movieBinding: MovieCardBinding) : RecyclerView.ViewHolder(movieBinding.root) {
    val binding = movieBinding
    fun onItemBind(item : MovieVo,context: Context ){
        Glide
            .with(context)
            .load("https://image.tmdb.org/t/p/original/${item.posterPath}")
            .centerCrop()
            .placeholder(R.drawable.hint_logo)
            .into(movieBinding.posterImageView);
        movieBinding.movieNameTextView.text = item.title
        movieBinding.ratingTextView.text = item.voteAverage.getOneDecimalValue().toString()
    }

}