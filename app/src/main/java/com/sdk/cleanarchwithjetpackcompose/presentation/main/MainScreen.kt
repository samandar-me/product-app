package com.sdk.cleanarchwithjetpackcompose.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sdk.cleanarchwithjetpackcompose.ui.theme.Purple500

@Composable
fun MainScreen(
    navController: NavController,
) {
    var isDialogShow by remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    var expanded by remember { mutableStateOf(false) }
    val activity = (LocalContext.current as MainActivity)
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
                        DropdownMenuItem(onClick = {
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
                onClick = { },
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
        LazyColumn {

        }
    }
}