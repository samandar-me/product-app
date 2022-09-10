package com.sdk.cleanarchwithjetpackcompose.data.product.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductCreateRequest(
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Int
)