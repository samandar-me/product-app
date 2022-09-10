package com.sdk.cleanarchwithjetpackcompose.domain.login.entity

data class LoginEntity(
    val id: Int,
    val name: String,
    val email: String,
    val token: String
)