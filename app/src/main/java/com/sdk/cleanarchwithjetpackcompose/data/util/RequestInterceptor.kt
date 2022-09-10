package com.sdk.cleanarchwithjetpackcompose.data.util

import com.sdk.cleanarchwithjetpackcompose.manager.SharedPrefManager
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor constructor(private val sharedPrefManager: SharedPrefManager) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sharedPrefManager.getToken()
        val newReq = chain.request().newBuilder().addHeader("Authorization", token).build()
        return chain.proceed(newReq)
    }
}