package com.example.wavewash.di.service

import com.example.wavewash.data.datastore.AppDataStore
import com.example.wavewash.data.remote.SillyApi
import com.example.wavewash.domain.use_cases.Login
import com.example.wavewash.domain.use_cases.Service
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideService(
        service: SillyApi,
        appDataStoreManager: AppDataStore,
    ): Service {
        return Service(
            service,
            appDataStoreManager
        )
    }

}