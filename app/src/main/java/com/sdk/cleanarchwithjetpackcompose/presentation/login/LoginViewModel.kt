package com.sdk.cleanarchwithjetpackcompose.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.cleanarchwithjetpackcompose.data.login.remote.dto.LoginRequest
import com.sdk.cleanarchwithjetpackcompose.domain.login.use_case.LoginUseCase
import com.sdk.cleanarchwithjetpackcompose.manager.SharedPrefManager
import com.sdk.cleanarchwithjetpackcompose.presentation.base.BaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {
    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Init)
    val state: StateFlow<LoginState> get() = _state

    private fun hideLoading() {
        _state.value = LoginState.IsLoading(false)
    }

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            loginUseCase(loginRequest)
                .onStart {
                    _state.value = LoginState.IsLoading(true)
                }
                .catch {
                    hideLoading()
                    _state.value = LoginState.ShowToast(it.stackTraceToString())
                }
                .collect { result ->
                    hideLoading()
                    when (result) {
                        is BaseResult.Error -> _state.value = LoginState.Error(result.rawResponse)
                        is BaseResult.Success -> _state.value = LoginState.Success(result.data)
                    }
                }
        }
    }

    fun saveToken(token: String) {
        sharedPrefManager.saveToken(token)
    }
}