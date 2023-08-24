package com.kkpfg.fundtrading.viewmodels

import TokenManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkpfg.fundtrading.data.api.APIClient
import com.kkpfg.fundtrading.data.api.models.BannerList
import com.kkpfg.fundtrading.data.api.models.FundList
import com.kkpfg.fundtrading.data.api.models.UserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl

class HomeViewModel(private val mockServer: HttpUrl? = null) : ViewModel() {

    private var pageSize = 10

    private val _userProfile = MutableLiveData<UserProfile>()
    val userProfile: LiveData<UserProfile>
        get() = _userProfile

    private val _fundList = MutableLiveData<FundList>()
    val fundList: LiveData<FundList>
        get() = _fundList

    private val _bannerList = MutableLiveData<BannerList>()
    val bannerList: LiveData<BannerList>
        get() = _bannerList

    fun loadProfile() {
        CoroutineScope(Dispatchers.IO).launch {
            val token = TokenManager.getTokenManager().getAccessToken()
            val profileAPI = APIClient.getProfileAPI(mockServer)
            val response = profileAPI.getProfile(token ?: "")
            withContext(Dispatchers.Main) {
                _userProfile.value = response.body()
            }
        }
    }

    fun loadFundListing(page: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val token = TokenManager.getTokenManager().getAccessToken()
            val profileAPI = APIClient.getFundAPI(mockServer)
            token?.let {
                val response = profileAPI.getFundListing(token, pageSize, page)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        _fundList.value = response.body()
                    }
                }
            }

        }
    }

    fun loadBanner() {
        CoroutineScope(Dispatchers.IO).launch {
            val token = TokenManager.getTokenManager().getAccessToken()
            val bannerAPI = APIClient.getBannerAPI(mockServer)
            token?.let {
                val result = bannerAPI.getBanners(token)
                if (result.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        _bannerList.value = result.body()
                    }
                }
            }
        }
    }
}