package com.kkpfg.fundtrading.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkpfg.fundtrading.data.api.APIClient
import com.kkpfg.fundtrading.data.api.RegisterRequest
import com.kkpfg.fundtrading.data.api.models.RegisterResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.HttpUrl
import retrofit2.Response


class RegisterViewModel(private val mockServer: HttpUrl? = null) : ViewModel() {

    private val _registerResponse = MutableLiveData<Response<RegisterResponse>>()
    val registerResponse: LiveData<Response<RegisterResponse>>
        get() = _registerResponse

    fun doRegister(email: String,password: String, firstname: String, lastname: String){
        val req = RegisterRequest(
            email,
            firstname,
            lastname,
            password
        )
        val registerAPI = APIClient.getAuthAPI(mockServer)

        CoroutineScope(Dispatchers.IO).launch {
            val response = registerAPI.registerUser(req)
            _registerResponse.value = response
        }
    }
}