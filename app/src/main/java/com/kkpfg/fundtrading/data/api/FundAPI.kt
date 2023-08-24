package com.kkpfg.fundtrading.data.api

import com.kkpfg.fundtrading.data.api.models.FundItem
import com.kkpfg.fundtrading.data.api.models.FundList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface FundAPI {

    @GET("/fund-listing")
    suspend fun getFundListing(
        @Header("Authorization") accessToken: String,
        @Query("page_size") pageSize: Int = 999,
        @Query("page") page: Int = 1,
    ): Response<FundList>

    @GET("/fund/{FUND_ID}")
    suspend fun getFundFromID(@Header("Authorization") accessToken: String,
                              @Path("FUND_ID") id: String): Response<FundItem>

}