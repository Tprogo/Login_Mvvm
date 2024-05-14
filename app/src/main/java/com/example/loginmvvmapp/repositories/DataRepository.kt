package com.example.loginmvvmapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.loginmvvmapp.authenticationFragments.UserDataApi
import com.example.loginmvvmapp.model.UserTwo
import com.example.loginmvvmapp.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class DataRepository @Inject constructor(private val userDataApi: UserDataApi) {

    private val _mutableData = MutableLiveData<NetworkResult<UserTwo>>()

    val liveData: LiveData<NetworkResult<UserTwo>>
        get() = _mutableData

    suspend fun getData(){
        val response= userDataApi.getData()
        _mutableData.postValue(NetworkResult.Loading())

        if (response.isSuccessful && response.body()!= null){
            _mutableData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null){
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _mutableData.postValue(NetworkResult.Error(errorObj.getString("error")))
        }else{
            _mutableData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}