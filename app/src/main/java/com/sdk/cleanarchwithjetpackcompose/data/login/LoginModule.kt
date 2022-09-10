package com.sdk.cleanarchwithjetpackcompose.data.login

import com.sdk.cleanarchwithjetpackcompose.data.login.remote.api.LoginApi
import com.sdk.cleanarchwithjetpackcompose.data.login.repository.LoginRepositoryImpl
import com.sdk.cleanarchwithjetpackcompose.di.NetworkModule
import com.sdk.cleanarchwithjetpackcompose.domain.login.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class LoginModule {

    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(api: LoginApi): LoginRepository {
        return LoginRepositoryImpl(api)
    }
}