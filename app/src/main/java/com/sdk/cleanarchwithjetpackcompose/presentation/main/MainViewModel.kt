package com.sdk.cleanarchwithjetpackcompose.presentation.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.cleanarchwithjetpackcompose.domain.product.entity.ProductEntity
import com.sdk.cleanarchwithjetpackcompose.domain.product.use_case.ProductUseCase
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
class MainViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {
    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState.Init)
    val state: StateFlow<MainState> get() = _state

    private val _productList: MutableState<List<ProductEntity>> = mutableStateOf(listOf())
    val productList: State<List<ProductEntity>> get() = _productList

    init {
        getAllMyProducts()
    }

    fun getAllMyProducts() {
        viewModelScope.launch {
            productUseCase.getAllMyProductUseCase()
                .onStart {
                    _state.value = MainState.Loading
                }
                .catch {
                    _state.value = MainState.Error(it.stackTraceToString())
                }
                .collect {
                    when(it) {
                        is BaseResult.Error -> _state.value = MainState.Error(it.rawResponse.message)
                        is BaseResult.Success -> _productList.value = it.data
                    }
                }
        }
    }
    fun logOut() {
        sharedPrefManager.clear()
    }
}