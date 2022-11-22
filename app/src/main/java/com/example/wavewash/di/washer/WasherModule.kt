package com.example.wavewash.di.washer

import com.example.wavewash.data.datastore.AppDataStore
import com.example.wavewash.data.local.SillyWashDatabase
import com.example.wavewash.data.local.order.OrderDao
import com.example.wavewash.data.local.service.ServiceDao
import com.example.wavewash.data.local.washer.WasherDao
import com.example.wavewash.data.remote.dto.service.ServiceApi
import com.example.wavewash.data.remote.dto.washer.WasherApi
import com.example.wavewash.domain.use_cases.ServiceUseCase
import com.example.wavewash.domain.use_cases.WasherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WasherModule {

    @Singleton
    @Provides
    fun provideWasherDao(db: SillyWashDatabase): WasherDao {
        return db.washerDao
    }

    @Singleton
    @Provides
    fun provideWasherApi(retrofitBuilder: Retrofit.Builder): WasherApi {
        return retrofitBuilder
            .build()
            .create(WasherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWasherUseCase(
        washerApi: WasherApi,
        appDataStoreManager: AppDataStore,
        washerDao: WasherDao,
        orderDao: OrderDao,
        serviceDao: ServiceDao
    ): WasherUseCase {
        return WasherUseCase(
            washerApi,
            appDataStoreManager,
            washerDao,
            orderDao,
            serviceDao
        )
    }
}