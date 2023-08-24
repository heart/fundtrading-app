package com.kkpfg.fundtrading.data.api.models

import com.google.gson.Gson
import okhttp3.ResponseBody

data class ErrorResponse(val error: String?){
    companion object{
        fun parseError(errorBody: ResponseBody?): ErrorResponse {
            val errorBodyString = errorBody?.string()
            val gson = Gson()
            return gson.fromJson(errorBodyString, ErrorResponse::class.java)
        }
    }
}