package com.kbz.mobiz.domain.data.vos

import com.google.gson.annotations.SerializedName


data class TrailerResponseVo (
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("results")
    val results: List<TrailerVo>? = null
)