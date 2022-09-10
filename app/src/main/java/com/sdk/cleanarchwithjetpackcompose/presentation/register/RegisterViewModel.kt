package com.sdk.cleanarchwithjetpackcompose.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.cleanarchwithjetpackcompose.data.register.remote.dto.RegisterRequest
import com.sdk.cleanarchwithjetpackcompose.domain.register.use_case.RegisterUseCase
import com.sdk.cleanarchwithjetpackcompose.manager.SharedPrefManager
import com.sdk.cleanarchwithjetpackcompose.presentation.base.BaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCase: RegisterUseCase,
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {
    private val _state: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState.Init)
    val state: StateFlow<RegisterState> get() = _state

    fun register(request: RegisterRequest) {
        viewModelScope.launch {
            useCase(request)
                .onStart {
                    _state.value = RegisterState.IsLoading(true)
                }
                .catch {
                    _state.value = RegisterState.IsLoading(false)
                    _state.value = RegisterState.ShowToast(it.stackTraceToString())
                }
                .collect {
                    _state.value = RegisterState.IsLoading(false)
                    when (it) {
                        is BaseResult.Success -> _state.value =
                            RegisterState.SuccessRegister(it.data)
                        is BaseResult.Error -> _state.value =
                            RegisterState.ErrorRegister(it.rawResponse)
                    }
                }
        }
    }
    fun saveToken(token: String) {
        sharedPrefManager.saveToken(token)
    }
}