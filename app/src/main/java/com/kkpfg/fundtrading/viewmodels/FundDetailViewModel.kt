package com.kkpfg.fundtrading.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkpfg.fundtrading.data.api.APIClient
import com.kkpfg.fundtrading.data.api.models.FundItem
import com.kkpfg.fundtrading.data.api.models.UserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl

class FundDetailViewModel(private val mockServer: HttpUrl? = null): ViewModel() {
    private val _userProfile = MutableLiveData<UserProfile>()
    val userProfile: LiveData<UserProfile>
        get() = _userProfile

    private val _fund = MutableLiveData<FundItem>()
    val fund: LiveData<FundItem>
        get() = _fund

    fun loadFundFromID(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            val token = TokenManager.getTokenManager().getAccessToken()
            token?.let{
                val fundAPI = APIClient.getFundAPI(mockServer)
                val response = fundAPI.getFundFromID(token, id)
                withContext(Dispatchers.Main) {
                    _fund.value = response.body()
                }
            }
        }
    }

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

}