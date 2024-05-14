package com.example.loginmvvmapp.di

import com.example.loginmvvmapp.authenticationFragments.AuthInterceptor
import com.example.loginmvvmapp.authenticationFragments.UserApiService
import com.example.loginmvvmapp.authenticationFragments.UserDataApi
import com.example.loginmvvmapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn (SingletonComponent::class)
class AuthModule {

//    create retrofit

    @Provides
    @Singleton
    fun retrofitbuilder(): Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl("https://reqres.in")
            .addConverterFactory(GsonConverterFactory.create())


    }



    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

//    create function and return UserApiService

    @Provides
    @Singleton
    fun getUserApiService(retrofitBuilder: Retrofit.Builder): UserApiService{
       return retrofitBuilder.build()
           .create(UserApiService::class.java)
    }
//
//    @Singleton
//    @Provides
//    fun provideAuthRetrofit(okHttpClient: OkHttpClient): Retrofit{
//        return Retrofit.Builder()
//            .baseUrl("https://reqres.in")
//            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }

    @Singleton
    @Provides
    fun getUserDataApi(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): UserDataApi{
        return retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(UserDataApi::class.java)
    }
}