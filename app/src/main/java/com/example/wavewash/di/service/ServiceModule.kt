package com.example.wavewash.di.service

import com.example.wavewash.data.datastore.AppDataStore
import com.example.wavewash.data.local.SillyWashDatabase
import com.example.wavewash.data.local.service.ServiceDao
import com.example.wavewash.data.remote.dto.service.ServiceApi
import com.example.wavewash.domain.use_cases.ServiceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideServiceDao(db: SillyWashDatabase): ServiceDao {
        return db.serviceDao
    }

    @Singleton
    @Provides
    fun provideServiceApi(retrofitBuilder: Retrofit.Builder): ServiceApi {
        return retrofitBuilder
            .build()
            .create(ServiceApi::class.java)
    }


    @Singleton
    @Provides
    fun provideService(
        serviceApi: ServiceApi,
        appDataStoreManager: AppDataStore,
        serviceDao: ServiceDao
    ): ServiceUseCase {
        return ServiceUseCase(
            serviceApi,
            appDataStoreManager,
            serviceDao
        )
    }

}