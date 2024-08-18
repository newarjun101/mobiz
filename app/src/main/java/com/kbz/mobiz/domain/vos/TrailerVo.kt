package com.kbz.mobiz.domain.vos

import com.google.gson.annotations.SerializedName


data class TrailerVo (
    @SerializedName("iso_639_1")
    val iso639_1: ISO639_1? = null,

    @SerializedName("iso_3166_1")
    val iso3166_1: ISO3166_1? = null,

    val name: String? = null,
    val key: String? = null,
    val site: Site? = null,
    val size: Long? = null,
    val type: Type? = null,
    val official: Boolean? = null,

    @SerializedName("published_at")
    val publishedAt: String? = null,

    val id: String? = null
)


enum class ISO3166_1(val value: String) {
    @SerializedName("US") Us("US");
}


enum class ISO639_1(val value: String) {
    @SerializedName("en") En("en");
}


enum class Site(val value: String) {
    @SerializedName("YouTube") YouTube("YouTube");
}


enum class Type(val value: String) {
    @SerializedName("Behind the Scenes") BehindTheScenes("Behind the Scenes"),
    @SerializedName("Bloopers") Bloopers("Bloopers"),
    @SerializedName("Featurette") Featurette("Featurette"),
    @SerializedName("Teaser") Teaser("Teaser"),
    @SerializedName("Trailer") Trailer("Trailer");
}
