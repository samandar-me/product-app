package com.sdk.cleanarchwithjetpackcompose.presentation.register

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sdk.cleanarchwithjetpackcompose.ui.theme.Purple500

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
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
            Button(
                onClick = {
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.border(2.dp, Purple500, shape = RectangleShape)
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "back",
                        tint = Purple500
                    )
                    Text(text = "Back", color = Purple500)
                }
            }
        }
    }
}