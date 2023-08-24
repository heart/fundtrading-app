package com.kkpfg.fundtrading.data.api

import com.kkpfg.fundtrading.data.api.models.RegisterResponse
import com.kkpfg.fundtrading.data.api.models.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

data class RegisterRequest(
    val email: String,
    val firstname: String,
    val lastname: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)





interface AuthAPI {
    @POST("/register")
    suspend fun registerUser(@Body user: RegisterRequest): Response<RegisterResponse>

    @POST("/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<TokenResponse>

    @POST("/refresh-token")
    suspend fun refreshToken(@Header("Authorization") refreshToken: String): Response<TokenResponse>

}




