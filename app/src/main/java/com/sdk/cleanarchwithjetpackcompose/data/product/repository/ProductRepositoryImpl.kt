package com.sdk.cleanarchwithjetpackcompose.data.product.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sdk.cleanarchwithjetpackcompose.data.product.remote.api.ProductApi
import com.sdk.cleanarchwithjetpackcompose.data.product.remote.dto.ProductCreateRequest
import com.sdk.cleanarchwithjetpackcompose.data.product.remote.dto.ProductResponse
import com.sdk.cleanarchwithjetpackcompose.data.product.remote.dto.ProductUpdateRequest
import com.sdk.cleanarchwithjetpackcompose.data.util.WrapperListResponse
import com.sdk.cleanarchwithjetpackcompose.data.util.WrapperResponse
import com.sdk.cleanarchwithjetpackcompose.domain.product.ProductRepository
import com.sdk.cleanarchwithjetpackcompose.domain.product.entity.ProductEntity
import com.sdk.cleanarchwithjetpackcompose.domain.product.entity.ProductUserEntity
import com.sdk.cleanarchwithjetpackcompose.presentation.base.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : ProductRepository {
    override suspend fun getAllMyProducts(): Flow<BaseResult<List<ProductEntity>, WrapperListResponse<ProductResponse>>> {
        return flow {
            val response = api.getAllMyProducts()
            if (response.isSuccessful) {
                val body = response.body()!!
                val products = mutableListOf<ProductEntity>()
                var user: ProductUserEntity?
                body.data?.forEach { product ->
                    user = ProductUserEntity(product.user.id, product.user.name, product.user.email)
                    products.add(
                        ProductEntity(
                            product.id,
                            product.name,
                            product.price,
                            user!!
                        )
                    )
                }
                emit(BaseResult.Success(products))
            } else {
                val type = object : TypeToken<WrapperListResponse<ProductResponse>>() {}.type
                val error: WrapperListResponse<ProductResponse> =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)!!
                error.code = response.code()
                emit(BaseResult.Error(error))
            }
        }
    }

    override suspend fun getProductById(id: String): Flow<BaseResult<ProductEntity, WrapperResponse<ProductResponse>>> {
        return flow {
            val response = api.getProductById(id)
            if (response.isSuccessful) {
                val body = response.body()!!
                val user = ProductUserEntity(
                    body.data?.user?.id!!,
                    body.data?.user?.name!!,
                    body.data?.user?.email!!
                )
                val product = ProductEntity(
                    body.data?.id!!,
                    body.data?.name!!,
                    body.data?.price!!,
                    user
                )
                emit(BaseResult.Success(product))
            } else {
                val type = object : TypeToken<WrapperResponse<ProductResponse>>() {}.type
                val error: WrapperResponse<ProductResponse> =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)!!
                error.code = response.code()
                emit(BaseResult.Error(error))
            }
        }
    }

    override suspend fun updateProduct(
        productUpdateRequest: ProductUpdateRequest,
        id: String
    ): Flow<BaseResult<ProductEntity, WrapperResponse<ProductResponse>>> {
        return flow {
            val response = api.updateProduct(productUpdateRequest, id)
            if (response.isSuccessful) {
                val body = response.body()!!
                val user = ProductUserEntity(
                    body.data?.user?.id!!,
                    body.data?.user?.name!!,
                    body.data?.user?.email!!
                )
                val product = ProductEntity(
                    body.data?.id!!,
                    body.data?.name!!,
                    body.data?.price!!,
                    user
                )
                emit(BaseResult.Success(product))
            } else {
                val typeToken = object : TypeToken<WrapperResponse<ProductResponse>>() {}.type
                val error: WrapperResponse<ProductResponse> =
                    Gson().fromJson(response.errorBody()!!.charStream(), typeToken)!!
                emit(BaseResult.Error(error))
            }
        }
    }

    override suspend fun deleteProduct(id: String): Flow<BaseResult<Unit, WrapperResponse<ProductResponse>>> {
        return flow {
            val response = api.deleteProduct(id)
            if (response.isSuccessful) {
                emit(BaseResult.Success(Unit))
            } else {
                val type = object : TypeToken<WrapperResponse<ProductResponse>>() {}.type
                val error: WrapperResponse<ProductResponse> =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)
                emit(BaseResult.Error(error))
            }
        }
    }

    override suspend fun createResponse(productCreateRequest: ProductCreateRequest): Flow<BaseResult<ProductEntity, WrapperResponse<ProductResponse>>> {
        return flow {
            val response = api.createProduct(productCreateRequest)
            if (response.isSuccessful) {
                val body = response.body()!!
                val user = ProductUserEntity(
                    body.data?.user?.id!!,
                    body.data?.user?.name!!,
                    body.data?.user?.email!!
                )
                val product = ProductEntity(
                    body.data?.id!!,
                    body.data?.name!!,
                    body.data?.price!!,
                    user
                )
                emit(BaseResult.Success(product))
            } else {
                val type = object : TypeToken<WrapperResponse<ProductResponse>>() {}.type
                val error: WrapperResponse<ProductResponse> =
                    Gson().fromJson(response.errorBody()!!.charStream(), type)!!
                error.code = response.code()
                emit(BaseResult.Error(error))
            }
        }
    }
}