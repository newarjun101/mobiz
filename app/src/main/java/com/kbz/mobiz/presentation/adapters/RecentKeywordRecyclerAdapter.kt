package com.kbz.mobiz.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kbz.mobiz.R
import com.kbz.mobiz.core.deligation.OnMovieClick
import com.kbz.mobiz.core.deligation.RecentClickDeligation
import com.kbz.mobiz.core.extension.getOneDecimalValue
import com.kbz.mobiz.databinding.MovieCardBinding
import com.kbz.mobiz.databinding.RecentCardBinding
import com.kbz.mobiz.domain.data.vos.MovieVo
import com.kbz.mobiz.domain.data.vos.RecentVo

class RecentKeywordRecyclerAdapter(private  val itemClickListener: RecentClickDeligation) : RecyclerView.Adapter<RecentKeywordRecyclerViewHolder>() {

    private lateinit var  context : Context
    private var mRecentList: MutableList<RecentVo> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentKeywordRecyclerViewHolder {

        context = parent.context
        val binding = RecentCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentKeywordRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  mRecentList.size
    }

    override fun onBindViewHolder(holder: RecentKeywordRecyclerViewHolder, position: Int) {
        holder.onItemBind(mRecentList[position],context)

        holder.binding.recentDelete.setOnClickListener{
            itemClickListener.onDeleteRecent(mRecentList[position])
        }
        holder.binding.recentCard.setOnClickListener {
            itemClickListener.onClickRecent(mRecentList[position])
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setMovies(recent : List<RecentVo>) {
        mRecentList = recent as ArrayList<RecentVo>
        notifyDataSetChanged()
    }

}

class RecentKeywordRecyclerViewHolder(private  val recentBinding: RecentCardBinding) : RecyclerView.ViewHolder(recentBinding.root) {
    val binding = recentBinding
    fun onItemBind(item : RecentVo, context: Context){
        recentBinding.recentTextView.text = item.title
    }

}