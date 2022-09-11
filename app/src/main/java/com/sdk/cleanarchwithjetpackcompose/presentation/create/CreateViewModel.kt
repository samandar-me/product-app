package com.sdk.cleanarchwithjetpackcompose.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.cleanarchwithjetpackcompose.data.product.remote.dto.ProductCreateRequest
import com.sdk.cleanarchwithjetpackcompose.domain.product.use_case.ProductUseCase
import com.sdk.cleanarchwithjetpackcompose.presentation.base.BaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val productUseCase: ProductUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<CreateState> = MutableStateFlow(CreateState.Init)
    val state: StateFlow<CreateState> get() = _state

    fun createProduct(createRequest: ProductCreateRequest) {
        viewModelScope.launch {
            productUseCase.createProductUseCase(createRequest)
                .onStart {
                    _state.value = CreateState.Loading
                }
                .catch {
                    _state.value = CreateState.ShowToast(it.stackTraceToString())
                }
                .collect {
                    when (it) {
                        is BaseResult.Error -> CreateState.ShowToast(it.rawResponse.message)
                        is BaseResult.Success -> CreateState.Success
                    }
                }
        }
    }
}