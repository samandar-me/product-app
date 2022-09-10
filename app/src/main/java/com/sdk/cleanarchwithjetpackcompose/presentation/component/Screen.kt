package com.sdk.cleanarchwithjetpackcompose.presentation.component

sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash_screen")
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("reg_screen")
    object MainScreen: Screen("main_screen")
    object CreateScreen: Screen("create_screen")
    object DetailScreen: Screen("detail_screen")
}