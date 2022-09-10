package com.sdk.cleanarchwithjetpackcompose.presentation.login

import com.sdk.cleanarchwithjetpackcompose.data.login.remote.dto.LoginResponse
import com.sdk.cleanarchwithjetpackcompose.data.util.WrapperResponse
import com.sdk.cleanarchwithjetpackcompose.domain.login.entity.LoginEntity

sealed class LoginState {
    object Init: LoginState()
    data class IsLoading(val loading: Boolean): LoginState()
    data class ShowToast(val message: String): LoginState()
    data class Success(val loginEntity: LoginEntity): LoginState()
    data class Error(val rawRes: WrapperResponse<LoginResponse>): LoginState()
}