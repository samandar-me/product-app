package com.sdk.cleanarchwithjetpackcompose.data.util

import com.google.gson.annotations.SerializedName

data class WrapperListResponse<T> (
    var code: Int,
    @SerializedName("status")
    var status: Boolean,
    @SerializedName("message")
    var message: String,
    @SerializedName("errors")
    var errors: List<String>? = null,
    @SerializedName("data")
    var data: List<T>? = null
)
data class WrapperResponse<T> (
    var code: Int,
    @SerializedName("status")
    var status: Boolean,
    @SerializedName("message")
    var message: String,
    @SerializedName("errors")
    var errors: List<String>? = null,
    @SerializedName("data")
    var data: T? = null
)