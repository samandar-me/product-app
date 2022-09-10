package com.sdk.cleanarchwithjetpackcompose.data.register.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sdk.cleanarchwithjetpackcompose.data.register.remote.api.RegisterApi
import com.sdk.cleanarchwithjetpackcompose.data.register.remote.dto.RegisterRequest
import com.sdk.cleanarchwithjetpackcompose.data.register.remote.dto.RegisterResponse
import com.sdk.cleanarchwithjetpackcompose.data.util.WrapperResponse
import com.sdk.cleanarchwithjetpackcompose.domain.register.RegisterRepository
import com.sdk.cleanarchwithjetpackcompose.domain.register.entity.RegisterEntity
import com.sdk.cleanarchwithjetpackcompose.presentation.base.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val api: RegisterApi
) : RegisterRepository {
    override suspend fun register(registerRequest: RegisterRequest): Flow<BaseResult<RegisterEntity, WrapperResponse<RegisterResponse>>> {
        return flow {
            val response = api.register(registerRequest)
            if (response.isSuccessful) {
                val body = response.body()!!
                val registerEntity = RegisterEntity(
                    body.data?.id!!,
                    body.data?.name!!,
                    body.data?.email!!,
                    body.data?.token!!
                )
                emit(BaseResult.Success(registerEntity))
            } else {
                val type = object : TypeToken<WrapperResponse<RegisterResponse>>() {}.type
                val error: WrapperResponse<RegisterResponse> =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)!!
                error.code = response.code()
                emit(BaseResult.Error(error))
            }
        }
    }
}