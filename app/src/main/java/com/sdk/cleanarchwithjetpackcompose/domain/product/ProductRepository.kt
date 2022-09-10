package com.sdk.cleanarchwithjetpackcompose.domain.product

import com.sdk.cleanarchwithjetpackcompose.data.product.remote.dto.ProductCreateRequest
import com.sdk.cleanarchwithjetpackcompose.data.product.remote.dto.ProductResponse
import com.sdk.cleanarchwithjetpackcompose.data.product.remote.dto.ProductUpdateRequest
import com.sdk.cleanarchwithjetpackcompose.data.util.WrapperListResponse
import com.sdk.cleanarchwithjetpackcompose.data.util.WrapperResponse
import com.sdk.cleanarchwithjetpackcompose.domain.product.entity.ProductEntity
import com.sdk.cleanarchwithjetpackcompose.presentation.base.BaseResult
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getAllMyProducts(): Flow<BaseResult<List<ProductEntity>, WrapperListResponse<ProductResponse>>>
    suspend fun getProductById(id: String): Flow<BaseResult<ProductEntity, WrapperResponse<ProductResponse>>>
    suspend fun updateProduct(productUpdateRequest: ProductUpdateRequest, id: String): Flow<BaseResult<ProductEntity, WrapperResponse<ProductResponse>>>
    suspend fun deleteProduct(id: String): Flow<BaseResult<Unit, WrapperResponse<ProductResponse>>>
    suspend fun createResponse(productCreateRequest: ProductCreateRequest): Flow<BaseResult<ProductEntity, WrapperResponse<ProductResponse>>>
}