package com.kkpfg.fundtrading.data.api

import android.util.Log
import com.kkpfg.fundtrading.utils.config.AppConfig
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    companion object{
        private var retrofit: Retrofit? = null
        private var profileAPI: ProfileAPI? = null
        private var authAPI: AuthAPI? = null
        private var fundAPI: FundAPI? = null
        private var bannerAPI: BannerAPI? = null

        private fun getRetrofit(mockServer: HttpUrl?): Retrofit {
            if (retrofit == null) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                val httpClient = OkHttpClient.Builder()
                httpClient.addInterceptor(logging)

                val gson = GsonConverterFactory.create()

//                val builder = Retrofit.Builder().also {
//                    when(mockServer){
//                        null->{
//                            Log.d("HTTP_API", "Real Server")
//                            it.baseUrl(AppConfig.baseUrl)
//                        }
//                        else->{ Log.d("HTTP_API", "Mock Server")
//                            it.baseUrl(mockServer)
//                        }
//                    }
//                    it.addConverterFactory(gson)
//                    it.client(httpClient.build())
//                }
//                retrofit = builder.build()

                val builder = Retrofit.Builder()
                    .baseUrl(AppConfig.baseUrl)
                    .addConverterFactory(gson)
                    .client(httpClient.build())
                retrofit = builder.build()

                return retrofit!!
            }

            return retrofit!!
        }

        fun getProfileAPI(mockServer: HttpUrl? = null): ProfileAPI {
            if(profileAPI == null){
                profileAPI = getRetrofit(mockServer).create(ProfileAPI::class.java)
            }
            return profileAPI!!
        }

        fun getAuthAPI(mockServer: HttpUrl? = null): AuthAPI {
            if(authAPI == null){
                authAPI = getRetrofit(mockServer).create(AuthAPI::class.java)
            }
            return authAPI!!
        }

        fun getFundAPI(mockServer: HttpUrl? = null): FundAPI {
            if(fundAPI == null){
                fundAPI = getRetrofit(mockServer).create(FundAPI::class.java)
            }
            return fundAPI!!
        }

        fun getBannerAPI(mockServer: HttpUrl? = null): BannerAPI {
            if(bannerAPI == null){
                bannerAPI = getRetrofit(mockServer).create(BannerAPI::class.java)
            }
            return bannerAPI!!
        }

    }
}