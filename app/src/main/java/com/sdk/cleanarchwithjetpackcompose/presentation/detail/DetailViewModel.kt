package com.sdk.cleanarchwithjetpackcompose.presentation.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.cleanarchwithjetpackcompose.data.product.remote.dto.ProductUpdateRequest
import com.sdk.cleanarchwithjetpackcompose.domain.product.entity.ProductEntity
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
class DetailViewModel @Inject constructor(
    private val productUseCase: ProductUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<DetailState> = MutableStateFlow(DetailState.Init)
    val state: StateFlow<DetailState> get() = _state

    private val _productEntity: MutableState<ProductEntity?> = mutableStateOf(null)
    val productEntity: State<ProductEntity?> get() = _productEntity

    fun getProductById(id: String) {
        viewModelScope.launch {
            productUseCase.getProductByIdUseCase(id)
                .onStart {
                    _state.value = DetailState.IsLoading
                }
                .catch {
                    _state.value = DetailState.ShowToast(it.stackTraceToString())
                }
                .collect {
                    when (it) {
                        is BaseResult.Error -> _state.value =
                            DetailState.ShowToast(it.rawResponse.message)
                        is BaseResult.Success -> _productEntity.value = it.data
                    }
                }
        }
    }

    fun updateProduct(productUpdateRequest: ProductUpdateRequest, id: String) {
        viewModelScope.launch {
            productUseCase.updateProductUseCase(productUpdateRequest, id)
                .onStart {
                    _state.value = DetailState.IsLoading
                }
                .catch {
                    _state.value = DetailState.ShowToast(it.stackTraceToString())
                }
                .collect {
                    when (it) {
                        is BaseResult.Error -> _state.value = DetailState.ShowToast(it.rawResponse.message)
                        is BaseResult.Success -> _state.value = DetailState.SuccessUpdate
                    }
                }
        }
    }

    fun deleteProduct(id: String) {
        viewModelScope.launch {
            productUseCase.deleteProductUseCase(id)
                .onStart {
                    _state.value = DetailState.IsLoading
                }
                .catch {
                    _state.value = DetailState.ShowToast(it.stackTraceToString())
                }
                .collect {
                    when (it) {
                        is BaseResult.Error -> _state.value = DetailState.ShowToast(it.rawResponse.message)
                        is BaseResult.Success -> _state.value = DetailState.SuccessDelete
                    }
                }
        }
    }
}