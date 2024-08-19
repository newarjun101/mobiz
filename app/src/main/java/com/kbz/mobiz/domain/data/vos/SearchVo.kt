package com.kbz.mobiz.domain.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "searchMovie")
  data class SearchVo (

    @PrimaryKey(autoGenerate = false)
    val id: Long? = null,

    @SerializedName("poster_path")
    @ColumnInfo("poster_path")
    val posterPath: String? = null,
    @SerializedName("title")
    @ColumnInfo("title")
    val title: String? = null,

    @SerializedName("vote_average")
    @ColumnInfo("vote_average")
    val voteAverage: Double? = null,
)

