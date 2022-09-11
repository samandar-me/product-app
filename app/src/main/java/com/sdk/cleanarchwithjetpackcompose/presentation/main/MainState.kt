package com.sdk.cleanarchwithjetpackcompose.presentation.main


sealed class MainState {
    object Init: MainState()
    object Loading: MainState()
    data class Error(val message: String): MainState()
}
