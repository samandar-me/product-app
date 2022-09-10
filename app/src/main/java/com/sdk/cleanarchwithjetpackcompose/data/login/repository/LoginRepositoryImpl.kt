package com.sdk.cleanarchwithjetpackcompose.data.login.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sdk.cleanarchwithjetpackcompose.data.login.remote.api.LoginApi
import com.sdk.cleanarchwithjetpackcompose.data.login.remote.dto.LoginRequest
import com.sdk.cleanarchwithjetpackcompose.data.login.remote.dto.LoginResponse
import com.sdk.cleanarchwithjetpackcompose.data.util.WrapperResponse
import com.sdk.cleanarchwithjetpackcompose.domain.login.entity.LoginEntity
import com.sdk.cleanarchwithjetpackcompose.domain.login.LoginRepository
import com.sdk.cleanarchwithjetpackcompose.presentation.base.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: LoginApi
) : LoginRepository {
    override suspend fun login(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, WrapperResponse<LoginResponse>>> {
        return flow {
            val response = api.login(loginRequest)
            if (response.isSuccessful) {
                val body = response.body()!!
                val loginEntity = LoginEntity(body.data?.id!!, body.data?.name!!, body.data?.email!!, body.data?.token!!)
                emit(BaseResult.Success(loginEntity))
            } else {
                val type = object : TypeToken<WrapperResponse<LoginResponse>>() {}.type
                val error: WrapperResponse<LoginResponse> = Gson().fromJson(response.errorBody()!!.charStream(), type)!!
                error.code = response.code()
                emit(BaseResult.Error(error))
            }
        }
    }
}