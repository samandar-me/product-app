package com.sdk.cleanarchwithjetpackcompose.data.register.remote.api

import com.sdk.cleanarchwithjetpackcompose.data.register.remote.dto.RegisterRequest
import com.sdk.cleanarchwithjetpackcompose.data.register.remote.dto.RegisterResponse
import com.sdk.cleanarchwithjetpackcompose.data.util.WrapperResponse
import com.sdk.cleanarchwithjetpackcompose.utils.Constants.REGISTER
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface RegisterApi {
    @GET(REGISTER)
    suspend fun register(@Body registerRequest: RegisterRequest): Response<WrapperResponse<RegisterResponse>>
}