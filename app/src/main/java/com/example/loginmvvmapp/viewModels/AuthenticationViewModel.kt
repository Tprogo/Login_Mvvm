package com.example.loginmvvmapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.loginmvvmapp.model.User
import com.example.loginmvvmapp.model.UserLoginResponse
import com.example.loginmvvmapp.model.UserResponse
import com.example.loginmvvmapp.repositories.UserRepository
import com.example.loginmvvmapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {

    //live data get after registration

    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = repository.userResponseLivedata

    //live data after login

    val userLoginResponseLiveData: LiveData<NetworkResult<UserLoginResponse>>
        get() = repository.userLoginResponseLiveData

    suspend fun registerUser(user: User){
        repository.registerUser(user)
    }

    suspend fun loginUser(user: User){
        repository.loginUser(user)
    }
}