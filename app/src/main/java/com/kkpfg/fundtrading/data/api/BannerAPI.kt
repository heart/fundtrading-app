package com.kkpfg.fundtrading.data.api

import com.kkpfg.fundtrading.data.api.models.BannerList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface BannerAPI {
    @GET("/banners")
    suspend fun getBanners(@Header("Authorization") accessToken: String): Response<BannerList>

}