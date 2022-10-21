package com.example.wavewash.di.order

import com.example.wavewash.data.datastore.AppDataStore
import com.example.wavewash.data.remote.SillyApi
import com.example.wavewash.domain.use_cases.Login
import com.example.wavewash.domain.use_cases.Order
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OrderModule {

    @Singleton
    @Provides
    fun provideOrder(
        service: SillyApi,
        appDataStoreManager: AppDataStore,
    ): Order {
        return Order(
            service,
            appDataStoreManager
        )
    }
}