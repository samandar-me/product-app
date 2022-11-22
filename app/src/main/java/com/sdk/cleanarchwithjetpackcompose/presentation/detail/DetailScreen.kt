package com.sdk.cleanarchwithjetpackcompose.presentation.detail

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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sdk.cleanarchwithjetpackcompose.data.product.remote.dto.ProductUpdateRequest
import com.sdk.cleanarchwithjetpackcompose.presentation.extensions.toast
import com.sdk.cleanarchwithjetpackcompose.ui.theme.Purple500

@Composable
fun DetailScreen(
    id: Int,
    name: String,
    price: String,
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState(initial = DetailState.Init).value
    var newName by remember { mutableStateOf(name) }
    var newPrice by remember { mutableStateOf(price) }
    val context = LocalContext.current
    var isDeleteClicked by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = Purple500,
                title = {
                    Text(text = name, fontSize = 18.sp, color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) {
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
                        viewModel.updateProduct(
                            ProductUpdateRequest(newName, newPrice.toInt()),
                            id.toString()
                        )
                    } else {
                        context.toast("Enter data!")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary)
            ) {
                Text(text = "Update", color = Color.White)
            }
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    isDeleteClicked = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text(text = "Delete", color = Color.White)
            }
        }
        if (state is DetailState.SuccessDelete) {
            LaunchedEffect(key1 = Unit) {
                navController.popBackStack()
            }
        }
        if (state is DetailState.SuccessUpdate) {
            context.toast("Successfully updated")
        }
        if (isDeleteClicked) {
            MaterialTheme {
                Column {
                    AlertDialog(
                        onDismissRequest = { isDeleteClicked = false },
                        title = {
                            Text(text = "Delete", color = Color.Red)
                        },
                        text = {
                            Text(text = "Do you want to delete $name?")
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    isDeleteClicked = false
                                    viewModel.deleteProduct(id.toString())
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Purple500)
                            ) {
                                Text(text = "Yes", color = Color.White)
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {
                                    isDeleteClicked = false
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Purple500)
                            ) {
                                Text(text = "No", color = Color.White)
                            }
                        }
                    )
                }
            }
        }
    }
}