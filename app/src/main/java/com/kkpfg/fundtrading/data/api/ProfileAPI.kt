package com.kkpfg.fundtrading.data.api

import com.kkpfg.fundtrading.data.api.models.UserProfile
import com.kkpfg.fundtrading.data.models.Deposit
import com.kkpfg.fundtrading.data.models.DepositResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part


interface ProfileAPI {
    @Multipart
    @POST("/upload-profile-picture")
    suspend fun uploadProfilePicture(@Header("Authorization") accessToken: String, @Part profilePicture: MultipartBody.Part): Response<Void>

    @GET("/profile")
    suspend fun getProfile(@Header("Authorization") accessToken: String): Response<UserProfile>

    @PUT("/profile")
    suspend fun updateProfile(@Header("Authorization") accessToken: String, @Body profile: UserProfile): Response<UserProfile>

    @POST("/deposit")
    suspend fun deposit(@Header("Authorization") accessToken: String, @Body deposit: Deposit): Response<DepositResponse>

}