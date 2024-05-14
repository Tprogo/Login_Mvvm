package com.example.loginmvvmapp.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.loginmvvmapp.authenticationFragments.UserApiService
import com.example.loginmvvmapp.model.User
import com.example.loginmvvmapp.model.UserLoginResponse
import com.example.loginmvvmapp.model.UserResponse
import com.example.loginmvvmapp.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApiService: UserApiService) {

    private val _userResponseLivedata = MutableLiveData<NetworkResult<UserResponse>>()

    val userResponseLivedata: LiveData<NetworkResult<UserResponse>>
        get() = _userResponseLivedata

    private val _userLoginResponseLiveData = MutableLiveData<NetworkResult<UserLoginResponse>>()

    val userLoginResponseLiveData: LiveData<NetworkResult<UserLoginResponse>>
        get() = _userLoginResponseLiveData

//    suspend fun registerUser(user: User){
//
//        _userResponseLivedata.postValue(NetworkResult.Loading())
//
//        val response = userApiService.registerUser(user)
//        Log.d("response Data","${response.body().toString()}")
//        Log.d("error response Data","${response.errorBody().toString()}")
//
//        handleResponse(response)
//    }


      suspend fun registerUser(user: User) {
    _userResponseLivedata.postValue(NetworkResult.Loading())

    try {
        val response = userApiService.registerUser(user)
        if (response.isSuccessful && response.body() != null) {
            _userResponseLivedata.postValue(NetworkResult.Success(response.body()!!))
        } else {
            handleErrorResponse(response)
        }
    } catch (e: IOException) {
        _userResponseLivedata.postValue(NetworkResult.Error("Network error occurred"))
    }
}

//    private fun handleResponse(response: Response<UserResponse>) {
//        if (response.isSuccessful && response.body() != null) {
//            _userResponseLivedata.postValue(NetworkResult.Success(response.body()!!))
//        } else if (response.errorBody() != null) {
//            //when we get error we have to parse error body
//
//            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
//            _userResponseLivedata.postValue(NetworkResult.Error(errorObj.getString("error")))
//        } else {
//            _userResponseLivedata.postValue(NetworkResult.Error("Something went wrong"))
//        }
//    }

        private fun handleErrorResponse(response: Response<UserResponse>) {
            val errorBody = response.errorBody()?.string()
            val errorMsg = errorBody?.let {
                try {
                    val jsonObject = JSONObject(it)
                    jsonObject.getString("error")
                } catch (e: Exception) {
                    "Unknown error"
                }
            } ?: "Unknown error"
            _userResponseLivedata.postValue(NetworkResult.Error(errorMsg))
        }

//    suspend fun loginUser(user:User){
//
//        _userLoginResponseLiveData.postValue(NetworkResult.Loading())
//        val loginresponse = userApiService.loginUser(user)
//
//
//        Log.d("login response Data","${loginresponse.body().toString()}")
//
//        Log.d("login error Data","${loginresponse.errorBody().toString()}")
//
//        if (loginresponse.isSuccessful && loginresponse.body() != null){
//            _userLoginResponseLiveData.postValue(NetworkResult.Success(loginresponse.body()!!))
//        } else if (loginresponse.errorBody() != null){
//            val loginError = JSONObject(loginresponse.errorBody()!!.charStream().readText())
//            _userLoginResponseLiveData.postValue(NetworkResult.Error(loginError.getString("error")))
//        } else {
//            _userLoginResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
//        }
//
//
//    }



    suspend fun loginUser(user: User) {
        _userLoginResponseLiveData.postValue(NetworkResult.Loading())

        try {
            val loginresponse = userApiService.loginUser(user)
            if (loginresponse.isSuccessful && loginresponse.body() != null) {
                _userLoginResponseLiveData.postValue(NetworkResult.Success(loginresponse.body()!!))
            } else {
                val loginError = JSONObject(loginresponse.errorBody()!!.charStream().readText())
            _userLoginResponseLiveData.postValue(NetworkResult.Error(loginError.getString("error")))
            }
        } catch (e: IOException) {
            _userLoginResponseLiveData.postValue(NetworkResult.Error("Network error occurred"))
        }
    }

//    private fun handleLoginErrorResponse(response: Response<UserLoginResponse>) {
//        val errorBody = response.errorBody()?.string()
//        val errorMsg = errorBody?.let {
//            try {
//                val jsonObject = JSONObject(it)
//                jsonObject.getString("error")
//            } catch (e: Exception) {
//                "Unknown error"
//            }
//        } ?: "Unknown error"
//        _userLoginResponseLiveData.postValue(NetworkResult.Error(errorMsg))
//    }
}



