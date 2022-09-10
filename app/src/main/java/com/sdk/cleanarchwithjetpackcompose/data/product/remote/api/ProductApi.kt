package com.sdk.cleanarchwithjetpackcompose.data.product.remote.api

import com.sdk.cleanarchwithjetpackcompose.data.product.remote.dto.ProductCreateRequest
import com.sdk.cleanarchwithjetpackcompose.data.product.remote.dto.ProductResponse
import com.sdk.cleanarchwithjetpackcompose.data.product.remote.dto.ProductUpdateRequest
import com.sdk.cleanarchwithjetpackcompose.data.util.WrapperListResponse
import com.sdk.cleanarchwithjetpackcompose.data.util.WrapperResponse
import com.sdk.cleanarchwithjetpackcompose.utils.Constants.PRODUCT
import com.sdk.cleanarchwithjetpackcompose.utils.Constants.PRODUCT_BY_ID
import retrofit2.Response
import retrofit2.http.*

interface ProductApi  {
    @GET(PRODUCT)
    suspend fun getAllMyProducts():Response<WrapperListResponse<ProductResponse>>

    @GET(PRODUCT_BY_ID)
    suspend fun getProductById(@Path("id") id: String): Response<WrapperResponse<ProductResponse>>

    @PUT(PRODUCT_BY_ID)
    suspend fun updateProduct(@Body productUpdateRequest: ProductUpdateRequest, @Path("id")id: String): Response<WrapperResponse<ProductResponse>>

    @DELETE(PRODUCT_BY_ID)
    suspend fun deleteProduct(@Path("id") id: String): Response<WrapperResponse<ProductResponse>>

    @POST(PRODUCT)
    suspend fun createProduct(@Body productCreateRequest: ProductCreateRequest): Response<WrapperResponse<ProductResponse>>
}