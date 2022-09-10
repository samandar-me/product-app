package com.sdk.cleanarchwithjetpackcompose.domain.register.use_case

import com.sdk.cleanarchwithjetpackcompose.data.register.remote.dto.RegisterRequest
import com.sdk.cleanarchwithjetpackcompose.data.register.remote.dto.RegisterResponse
import com.sdk.cleanarchwithjetpackcompose.data.util.WrapperResponse
import com.sdk.cleanarchwithjetpackcompose.domain.register.RegisterRepository
import com.sdk.cleanarchwithjetpackcompose.domain.register.entity.RegisterEntity
import com.sdk.cleanarchwithjetpackcompose.presentation.base.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: RegisterRepository
) {
    suspend operator fun invoke(request: RegisterRequest): Flow<BaseResult<RegisterEntity, WrapperResponse<RegisterResponse>>> {
        return repository.register(request)
    }
}