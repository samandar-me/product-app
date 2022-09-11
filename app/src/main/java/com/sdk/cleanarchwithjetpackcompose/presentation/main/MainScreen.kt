package com.sdk.cleanarchwithjetpackcompose.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sdk.cleanarchwithjetpackcompose.domain.product.entity.ProductEntity
import com.sdk.cleanarchwithjetpackcompose.presentation.component.Screen
import com.sdk.cleanarchwithjetpackcompose.presentation.extensions.toast
import com.sdk.cleanarchwithjetpackcompose.ui.theme.Purple500
import timber.log.Timber

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    var isDialogShow by remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    var expanded by remember { mutableStateOf(false) }
    val activity = (LocalContext.current as MainActivity)
    var fabClicked by remember { mutableStateOf(false) }
    val state = viewModel.state.collectAsState().value
    val productList by remember { viewModel.productList }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Your Products",
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                backgroundColor = Purple500,
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "more",
                            tint = Color.White
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                isDialogShow = true
                            }) {
                            Text(text = "Log out", color = Color.Red)
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    fabClicked = true
                },
                backgroundColor = Purple500
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "edit",
                    tint = Color.White
                )
            }
        }
    ) {
        if (fabClicked) {
            LaunchedEffect(key1 = true) {
                navController.navigate(Screen.CreateScreen.route)
                fabClicked = false
            }
        }
        if (isDialogShow) {
            MaterialTheme {
                Column {
                    AlertDialog(
                        onDismissRequest = { isDialogShow = false },
                        title = {
                            Text(text = "Log out", color = Color.Red)
                        },
                        text = {
                            Text(text = "Do you want to log out?")
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    isDialogShow = false
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Purple500)
                            ) {
                                Text(text = "No", color = Color.White)
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {
                                    isDialogShow = false
                                    activity.finish()
                                    viewModel.logOut()
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Purple500)
                            ) {
                                Text(text = "Yes", color = Color.White)
                            }
                        }
                    )
                }
            }
        }
        when (state) {
            is MainState.Init -> Unit
            is MainState.Loading -> Unit
            is MainState.Error -> {
                LocalContext.current.toast(state.message)
                Timber.tag("@@@").d(state.message)
            }
        }
        LazyColumn(
            contentPadding = PaddingValues(5.dp)
        ) {
            items(productList) {
                ProductItem(it, navController)
            }
        }
    }
}

@Composable
fun ProductItem(
    productEntity: ProductEntity,
    navController: NavController
) {
    var itemClicked by remember { mutableStateOf(false) }
    if (itemClicked) {
        LaunchedEffect(key1 = true) {
            navController.navigate(
                route = "${Screen.DetailScreen.route}/${productEntity.name}/${productEntity.price}/${productEntity.id}"
            )
        }
        itemClicked = false
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .padding(4.dp)
            .clickable {
                itemClicked = true
            },
        shape = RoundedCornerShape(4.dp),
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                text = productEntity.name,
                color = Color.Black,
                modifier = Modifier
                    .padding(10.dp),
                fontSize = 17.sp
            )
        }
    }
}