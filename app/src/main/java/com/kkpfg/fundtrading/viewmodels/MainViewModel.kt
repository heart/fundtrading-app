package com.kkpfg.fundtrading.viewmodels

import TokenManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkpfg.fundtrading.data.api.APIClient
import com.kkpfg.fundtrading.data.api.LoginRequest
import com.kkpfg.fundtrading.data.api.models.ErrorResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl

class MainViewModel(private val mockServer: HttpUrl? = null) : ViewModel() {

    private val _isLoginSuccess = MutableLiveData<Boolean>()
    val isLoginSuccess: LiveData<Boolean>
        get() = _isLoginSuccess


    private val _loginErrorMessage = MutableLiveData<String>()
    val loginErrorMessage: LiveData<String>
        get() = _loginErrorMessage


    fun doLogin(email: String, password:String){

        val authAPI = APIClient.getAuthAPI(mockServer)

        CoroutineScope(Dispatchers.IO).launch {
            val loginRequest = LoginRequest(password=password, email = email)
            val response = authAPI.loginUser(loginRequest)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let{
                        val accessToken = it.accessToken
                        val refreshToken = it.refreshToken
                        if(accessToken != null && refreshToken != null){
                            TokenManager.getTokenManager().saveTokens(accessToken,refreshToken)
                            _isLoginSuccess.value = true
                        }
                    }
                } else {
                    val error = ErrorResponse.parseError(response.errorBody())
                    _loginErrorMessage.value = error.error ?: ""
                }
            }
        }
    }

    fun resumeSession() {

        val refreshToken = TokenManager.getTokenManager().getRefreshToken()

        if(refreshToken != null){
            val authAPI = APIClient.getAuthAPI(mockServer)
            CoroutineScope(Dispatchers.IO).launch {
                val response = authAPI.refreshToken(refreshToken)
                response.body()?.let { token ->
                    if(response.isSuccessful){
                        if(token.accessToken != null &&  token.refreshToken != null){
                            withContext(Dispatchers.Main){
                                TokenManager.getTokenManager().saveTokens( token.accessToken,  token.refreshToken)
                                _isLoginSuccess.value = true
                            }
                        }
                    }
                }
            }

        }
    }



}