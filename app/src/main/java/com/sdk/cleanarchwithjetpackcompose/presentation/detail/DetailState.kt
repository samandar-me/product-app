package com.sdk.cleanarchwithjetpackcompose.presentation.detail

sealed class DetailState {
    object Init: DetailState()
    object IsLoading: DetailState()
    object SuccessDelete: DetailState()
    object SuccessUpdate: DetailState()
    data class ShowToast(val message: String): DetailState()
}