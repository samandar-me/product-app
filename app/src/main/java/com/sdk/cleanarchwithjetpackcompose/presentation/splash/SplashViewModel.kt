package com.sdk.cleanarchwithjetpackcompose.presentation.splash

import androidx.lifecycle.ViewModel
import com.sdk.cleanarchwithjetpackcompose.manager.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedPrefManager: SharedPrefManager
): ViewModel() {
    fun isAuthenticated(): Boolean = sharedPrefManager.getToken().isEmpty()
}