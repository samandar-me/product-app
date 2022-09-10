package com.sdk.cleanarchwithjetpackcompose.presentation.login

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sdk.cleanarchwithjetpackcompose.R
import com.sdk.cleanarchwithjetpackcompose.data.login.remote.dto.LoginRequest
import com.sdk.cleanarchwithjetpackcompose.presentation.component.Screen
import com.sdk.cleanarchwithjetpackcompose.presentation.extensions.isEmail
import com.sdk.cleanarchwithjetpackcompose.presentation.extensions.toast
import com.sdk.cleanarchwithjetpackcompose.ui.theme.Purple500

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState(initial = LoginState.Init).value
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = stringResource(id = R.string.sign_in),
                    fontSize = 28.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(18.dp))
                LinearProgress(state)
                Spacer(modifier = Modifier.height(35.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = stringResource(id = R.string.enter_em)) },
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = stringResource(id = R.string.enter_pass)) },
                    maxLines = 1,
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = "Eye"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        if (email.isEmail() && password.length > 8) {
                            val loginRequest = LoginRequest(email, password)
                            viewModel.login(loginRequest)
                        } else {
                            context.toast("Enter your data!")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(text = stringResource(id = R.string.sign_in), color = Color.White)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
            ) {
                Text(
                    text = stringResource(id = R.string.button_text_dont_have_account),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.RegisterScreen.route)
                        },
                    textAlign = TextAlign.Center,
                    color = Purple500
                )
                when (state) {
                    is LoginState.IsLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.fillMaxSize(),
                            color = Purple500
                        )
                    }
                    is LoginState.Init -> Unit
                    is LoginState.Error -> context.toast(state.rawRes.message)
                    is LoginState.ShowToast -> context.toast(state.message)
                    is LoginState.Success -> {
                        viewModel.saveToken(state.loginEntity.token)
                        navController.navigate(Screen.MainScreen.route)
                    }
                }
            }
        }
    }
}

@Composable
fun LinearProgress(state: LoginState) {
    val animatedProgress = animateFloatAsState(
        targetValue = if (state is LoginState.IsLoading) 1f else 0f,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value
    LinearProgressIndicator(progress = animatedProgress, modifier = Modifier.fillMaxWidth())
}


