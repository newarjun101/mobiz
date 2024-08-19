package com.kbz.mobiz.domain.data.vos

import com.google.gson.annotations.SerializedName


data class MovieResponseVo (
    val page: Long? = null,

    @SerializedName("results")
    val results: List<MovieVo>? = null,

    @SerializedName("total_pages")
    val totalPages: Long? = null,

    @SerializedName("total_results")
    val totalResults: Long? = null
)


