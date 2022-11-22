package com.sdk.cleanarchwithjetpackcompose.presentation.create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sdk.cleanarchwithjetpackcompose.data.product.remote.dto.ProductCreateRequest
import com.sdk.cleanarchwithjetpackcompose.presentation.extensions.toast
import com.sdk.cleanarchwithjetpackcompose.ui.theme.Purple500

@Composable
fun CreateScreen(
    navController: NavController,
    viewModel: CreateViewModel = hiltViewModel()
) {
    var newName by remember { mutableStateOf("") }
    var newPrice by remember { mutableStateOf("") }
    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Create Product", color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                backgroundColor = Purple500
            )
        }
    ) {
        if (state == CreateState.Loading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        if (state == CreateState.Success) {
            LaunchedEffect(key1 = Unit) {
                navController.popBackStack()
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                value = newName,
                onValueChange = { newName = it },
                maxLines = 2,
                label = { Text(text = "Enter new name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                value = newPrice,
                onValueChange = { newPrice = it },
                maxLines = 1,
                label = { Text(text = "Enter new price") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(35.dp))
            Button(
                onClick = {
                    if (newName.isNotBlank() && newPrice.isNotBlank()) {
                        viewModel.createProduct(
                            ProductCreateRequest(newName, newPrice.toInt())
                        )
                    } else {
                        context.toast("Enter data!")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(Color.Green)
            ) {
                Text(text = "Create", color = Color.White)
            }
        }
    }
}
//when (state) {
//                is CreateState.Init -> Unit
//                is CreateState.Loading -> {
//                    Column(
//                        modifier = Modifier.fillMaxSize(),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        CircularProgressIndicator()
//                    }
//                }
//                is CreateState.ShowToast -> context.toast(state.message)
//                is CreateState.Success -> context.toast("Successfully created!")
//            }