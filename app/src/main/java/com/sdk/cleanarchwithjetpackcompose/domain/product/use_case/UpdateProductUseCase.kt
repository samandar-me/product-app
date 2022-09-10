package com.sdk.cleanarchwithjetpackcompose.domain.product.use_case

import com.sdk.cleanarchwithjetpackcompose.data.product.remote.dto.ProductResponse
import com.sdk.cleanarchwithjetpackcompose.data.product.remote.dto.ProductUpdateRequest
import com.sdk.cleanarchwithjetpackcompose.data.util.WrapperResponse
import com.sdk.cleanarchwithjetpackcompose.domain.product.ProductRepository
import com.sdk.cleanarchwithjetpackcompose.domain.product.entity.ProductEntity
import com.sdk.cleanarchwithjetpackcompose.presentation.base.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(updateRequest: ProductUpdateRequest, id: String): Flow<BaseResult<ProductEntity, WrapperResponse<ProductResponse>>> {
        return repository.updateProduct(updateRequest, id)
    }
}