package com.example.wavewash.di.auth

import com.example.wavewash.data.datastore.AppDataStore
import com.example.wavewash.data.remote.SillyApi
import com.example.wavewash.domain.use_cases.Login
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun provideLogin(
        service: SillyApi,
        appDataStoreManager: AppDataStore,
    ): Login {
        return Login(
            service,
            appDataStoreManager
        )
    }
}