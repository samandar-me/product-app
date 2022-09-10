package com.sdk.cleanarchwithjetpackcompose.domain.product.use_case

data class ProductUseCase(
    val createProductUseCase: CreateProductUseCase,
    val deleteProductUseCase: DeleteProductUseCase,
    val getAllMyProductUseCase: GetAllMyProductUseCase,
    val getProductByIdUseCase: GetProductByIdUseCase,
    val updateProductUseCase: UpdateProductUseCase
)