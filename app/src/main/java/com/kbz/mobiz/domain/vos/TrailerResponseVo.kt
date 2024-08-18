package com.kbz.mobiz.domain.vos

import com.google.gson.annotations.SerializedName


data class TrailerResponseVo (
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("results")
    val results: List<TrailerVo>? = null
)