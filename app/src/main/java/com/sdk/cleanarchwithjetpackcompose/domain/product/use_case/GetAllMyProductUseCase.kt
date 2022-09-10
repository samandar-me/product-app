package com.sdk.cleanarchwithjetpackcompose.domain.product.use_case

import com.sdk.cleanarchwithjetpackcompose.data.product.remote.dto.ProductResponse
import com.sdk.cleanarchwithjetpackcompose.data.util.WrapperListResponse
import com.sdk.cleanarchwithjetpackcompose.domain.product.ProductRepository
import com.sdk.cleanarchwithjetpackcompose.domain.product.entity.ProductEntity
import com.sdk.cleanarchwithjetpackcompose.presentation.base.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMyProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke():Flow<BaseResult<List<ProductEntity>, WrapperListResponse<ProductResponse>>> {
        return repository.getAllMyProducts()
    }
}