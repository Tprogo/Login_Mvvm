package com.example.loginmvvmapp.authenticationFragments

import androidx.lifecycle.LiveData
import com.example.loginmvvmapp.model.User
import com.example.loginmvvmapp.model.UserLoginResponse
import com.example.loginmvvmapp.model.UserResponse
import com.example.loginmvvmapp.model.UserTwo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiService {

    //if you re using suspend fun in repoitory then use here also


    @POST("/api/register")
    suspend fun registerUser(@Body user: User): Response<UserResponse>

    @POST("/api/login")
    suspend fun loginUser(@Body user: User): Response<UserLoginResponse>


}