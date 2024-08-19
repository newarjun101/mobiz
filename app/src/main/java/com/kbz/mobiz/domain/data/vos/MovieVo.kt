package com.kbz.mobiz.domain.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.kbz.mobiz.core.mapper.Mapper


@Entity(tableName = "popularMovie")
data class MovieVo (
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
) : Mapper<MovieVo, SearchVo>  {
    override fun  mapper(): SearchVo {
        return SearchVo(
            id = this.id,
            posterPath = posterPath,
            title = title,
            voteAverage = voteAverage
        )
    }

}





