package com.example.loginmvvmapp.authenticationFragments

import com.example.loginmvvmapp.utils.TokenManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor()  : Interceptor {

    @Inject
    lateinit var tokenManager: TokenManager
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        GlobalScope.launch {
        val token = tokenManager.getToken()
        request.addHeader("Authorization","Bearer $token")}

        return chain.proceed(request.build())
    }
}