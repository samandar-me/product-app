package com.sdk.cleanarchwithjetpackcompose.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sdk.cleanarchwithjetpackcompose.presentation.component.Screen
import com.sdk.cleanarchwithjetpackcompose.presentation.create.CreateScreen
import com.sdk.cleanarchwithjetpackcompose.presentation.detail.DetailScreen
import com.sdk.cleanarchwithjetpackcompose.presentation.login.LoginScreen
import com.sdk.cleanarchwithjetpackcompose.presentation.register.RegisterScreen
import com.sdk.cleanarchwithjetpackcompose.presentation.splash.SplashScreen
import com.sdk.cleanarchwithjetpackcompose.ui.theme.CleanArchWithJetpackComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanArchWithJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.SplashScreen.route
                    ) {
                        composable(route = Screen.SplashScreen.route) {
                            SplashScreen(navController = navController)
                        }
                        composable(route = Screen.LoginScreen.route) {
                            LoginScreen(navController)
                        }
                        composable(route = Screen.RegisterScreen.route) {
                            RegisterScreen(navController = navController)
                        }
                        composable(route = Screen.MainScreen.route) {
                            MainScreen(navController = navController)
                        }
                        composable(route = Screen.CreateScreen.route) {
                            CreateScreen(navController = navController)
                        }
                        composable(
                            route = "${Screen.DetailScreen.route}/{name}/{price}/{id}",
                            arguments = listOf(
                                navArgument(name = "name") {
                                    type = NavType.StringType
                                },
                                navArgument(name = "price") {
                                    type = NavType.StringType
                                },
                                navArgument(name = "id") {
                                    type = NavType.IntType
                                }
                            )
                        ) { navBackState ->
                            val id = remember { navBackState.arguments?.getInt("id") ?: 0 }
                            val name =
                                remember { navBackState.arguments?.getString("name") ?: "No" }
                            val price =
                                remember { navBackState.arguments?.getString("price") ?: "No" }
                            DetailScreen(
                                id = id,
                                name = name,
                                price = price,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
