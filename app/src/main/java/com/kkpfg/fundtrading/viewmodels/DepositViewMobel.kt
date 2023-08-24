package com.kkpfg.fundtrading.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkpfg.fundtrading.data.api.APIClient
import com.kkpfg.fundtrading.data.api.RegisterRequest
import com.kkpfg.fundtrading.data.api.models.UserProfile
import com.kkpfg.fundtrading.data.models.Deposit
import com.kkpfg.fundtrading.data.models.DepositResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl

class DepositViewMobel(private val mockServer: HttpUrl? = null): ViewModel() {
    private val _depositResponse = MutableLiveData<DepositResponse>()
    val depositResponse: LiveData<DepositResponse>
        get() = _depositResponse

    fun doDeposit(amount: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val profileAPI = APIClient.getProfileAPI(mockServer)
            val token = TokenManager.getTokenManager().getAccessToken()
            token?.let{
                val deposit = Deposit(amount=amount)
                val response = profileAPI.deposit(token, deposit)
                withContext(Dispatchers.Main) {
                    _depositResponse.value = response.body()
                }
            }
        }
    }
}