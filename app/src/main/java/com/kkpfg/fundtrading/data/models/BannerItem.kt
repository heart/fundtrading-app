package com.kkpfg.fundtrading.data.api.models
import com.google.gson.annotations.SerializedName


data class BannerList(
    @SerializedName("banners")
    val banners: List<BannerItem>? = listOf()
)

data class BannerItem(
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("short_description")
    val shortDescription: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("url")
    val url: String? = null
)