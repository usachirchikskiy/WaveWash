package com.example.wavewash.di.washer

import com.example.wavewash.data.datastore.AppDataStore
import com.example.wavewash.data.remote.SillyApi
import com.example.wavewash.domain.use_cases.Login
import com.example.wavewash.domain.use_cases.Washer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WasherModule {

    @Singleton
    @Provides
    fun provideWasher(
        service: SillyApi,
        appDataStoreManager: AppDataStore,
    ): Washer {
        return Washer(
            service,
            appDataStoreManager
        )
    }
}