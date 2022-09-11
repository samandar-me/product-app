package com.sdk.cleanarchwithjetpackcompose.presentation.create

sealed class CreateState {
    object Init: CreateState()
    data class ShowToast(val message: String): CreateState()
    object Loading: CreateState()
    object Success: CreateState()
}