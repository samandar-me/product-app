package com.sdk.cleanarchwithjetpackcompose.presentation.extensions

import android.content.Context
import android.util.Patterns
import android.widget.Toast

fun String.isEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()
fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}