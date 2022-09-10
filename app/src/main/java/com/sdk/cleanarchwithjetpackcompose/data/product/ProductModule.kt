package com.sdk.cleanarchwithjetpackcompose.data.product

import com.sdk.cleanarchwithjetpackcompose.data.product.remote.api.ProductApi
import com.sdk.cleanarchwithjetpackcompose.data.product.repository.ProductRepositoryImpl
import com.sdk.cleanarchwithjetpackcompose.di.NetworkModule
import com.sdk.cleanarchwithjetpackcompose.domain.product.ProductRepository
import com.sdk.cleanarchwithjetpackcompose.domain.product.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class ProductModule {

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }
    @Provides
    @Singleton
    fun provideProductRepository(api: ProductApi): ProductRepository {
        return ProductRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideProductUseCases(repository: ProductRepository): ProductUseCase {
        return ProductUseCase(
            createProductUseCase = CreateProductUseCase(repository),
            deleteProductUseCase = DeleteProductUseCase(repository),
            getAllMyProductUseCase = GetAllMyProductUseCase(repository),
            getProductByIdUseCase = GetProductByIdUseCase(repository),
            updateProductUseCase = UpdateProductUseCase(repository)
        )
    }
}