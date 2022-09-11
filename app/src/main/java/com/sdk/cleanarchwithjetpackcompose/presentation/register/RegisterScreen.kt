package com.sdk.cleanarchwithjetpackcompose.presentation.register

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import com.sdk.cleanarchwithjetpackcompose.data.register.remote.dto.RegisterRequest
import com.sdk.cleanarchwithjetpackcompose.presentation.component.Screen
import com.sdk.cleanarchwithjetpackcompose.presentation.extensions.isEmail
import com.sdk.cleanarchwithjetpackcompose.presentation.extensions.toast
import com.sdk.cleanarchwithjetpackcompose.presentation.login.LoginState
import com.sdk.cleanarchwithjetpackcompose.ui.theme.Purple500

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var name by remember { mutableStateOf("") }
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
            when (val result = state) {
                is RegisterState.IsLoading -> Unit
                is RegisterState.Init -> Unit
                is RegisterState.ErrorRegister -> context.toast(result.rawResponse.message)
                is RegisterState.ShowToast -> context.toast(result.message)
                is RegisterState.SuccessRegister -> {
                    LaunchedEffect(key1 = true) {
                        viewModel.saveToken(result.registerEntity.token)
                        navController.navigate(Screen.MainScreen.route) {
                            popUpTo(Screen.RegisterScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
            Button(
                onClick = {
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .border(2.dp, Purple500, shape = RectangleShape)
                    .align(Alignment.Start)
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "back",
                        tint = Purple500
                    )
                    Text(text = "Back", color = Purple500, textAlign = TextAlign.Center)
                }
            }
            Text(
                text = stringResource(id = R.string.header_register),
                fontSize = 28.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(18.dp))
            LinearProgress(state = state)
            Spacer(modifier = Modifier.height(18.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = stringResource(id = R.string.enter_name)) },
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(15.dp))
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
                    if (name.isNotEmpty() && email.isEmail() && password.length > 8) {
                        val loginRequest = RegisterRequest(name, email, password)
                        viewModel.register(loginRequest)
                    } else {
                        context.toast("Enter your data!")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .align(Alignment.CenterHorizontally)
                    .height(55.dp),
            ) {
                Text(text = stringResource(id = R.string.header_register), color = Color.White)
            }
        }
    }
}

@Composable
fun LinearProgress(state: RegisterState) {
    val animatedProgress = animateFloatAsState(
        targetValue = if (state is RegisterState.IsLoading) 1f else 0f,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value
    LinearProgressIndicator(progress = animatedProgress, modifier = Modifier.fillMaxWidth())
}