package com.sdk.cleanarchwithjetpackcompose.presentation.register

import com.sdk.cleanarchwithjetpackcompose.data.register.remote.dto.RegisterResponse
import com.sdk.cleanarchwithjetpackcompose.data.util.WrapperResponse
import com.sdk.cleanarchwithjetpackcompose.domain.register.entity.RegisterEntity

sealed class RegisterState {
    object Init : RegisterState()
    data class IsLoading(val isLoading: Boolean) : RegisterState()
    data class ShowToast(val message: String) : RegisterState()
    data class SuccessRegister(val registerEntity: RegisterEntity) : RegisterState()
    data class ErrorRegister(val rawResponse: WrapperResponse<RegisterResponse>) : RegisterState()
}