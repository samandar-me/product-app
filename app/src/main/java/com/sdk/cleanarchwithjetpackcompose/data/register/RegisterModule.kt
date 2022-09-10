package com.sdk.cleanarchwithjetpackcompose.data.register

import com.sdk.cleanarchwithjetpackcompose.data.register.remote.api.RegisterApi
import com.sdk.cleanarchwithjetpackcompose.data.register.repository.RegisterRepositoryImpl
import com.sdk.cleanarchwithjetpackcompose.di.NetworkModule
import com.sdk.cleanarchwithjetpackcompose.domain.register.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class RegisterModule {
    @Provides
    @Singleton
    fun provideRegisterApi(retrofit: Retrofit): RegisterApi {
        return retrofit.create(RegisterApi::class.java)
    }
    @Provides
    @Singleton
    fun provideRegisterRepository(api: RegisterApi): RegisterRepository {
        return RegisterRepositoryImpl(api)
    }
}