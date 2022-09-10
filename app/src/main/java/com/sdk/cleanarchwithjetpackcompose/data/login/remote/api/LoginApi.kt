package com.sdk.cleanarchwithjetpackcompose.data.login.remote.api

import com.sdk.cleanarchwithjetpackcompose.data.login.remote.dto.LoginRequest
import com.sdk.cleanarchwithjetpackcompose.data.login.remote.dto.LoginResponse
import com.sdk.cleanarchwithjetpackcompose.data.util.WrapperResponse
import com.sdk.cleanarchwithjetpackcompose.utils.Constants.POST
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST(POST)
    suspend fun login(@Body loginRequest: LoginRequest): Response<WrapperResponse<LoginResponse>>
}