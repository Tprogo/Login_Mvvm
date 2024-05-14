package com.example.loginmvvmapp.authenticationFragments

import androidx.lifecycle.LiveData
import com.example.loginmvvmapp.model.UserTwo
import retrofit2.Response
import retrofit2.http.GET

interface UserDataApi {
    @GET("/api/users/2")
    suspend fun getData(): Response<UserTwo>
}