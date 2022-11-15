package com.example.wavewash.di.order

import com.example.wavewash.data.datastore.AppDataStore
import com.example.wavewash.data.local.SillyWashDatabase
import com.example.wavewash.data.local.order.OrderDao
import com.example.wavewash.data.local.service.ServiceDao
import com.example.wavewash.data.local.washer.WasherDao
import com.example.wavewash.data.remote.SillyWashApi
import com.example.wavewash.data.remote.dto.order.OrderApi
import com.example.wavewash.data.remote.dto.service.ServiceApi
import com.example.wavewash.domain.use_cases.OrderUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OrderModule {

    @Singleton
    @Provides
    fun provideOrderDao(db: SillyWashDatabase): OrderDao {
        return db.orderDao
    }

    @Singleton
    @Provides
    fun provideOrderApi(retrofitBuilder: Retrofit.Builder): OrderApi {
        return retrofitBuilder
            .build()
            .create(OrderApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOrder(
        orderApi: OrderApi,
        appDataStoreManager: AppDataStore,
        orderDao: OrderDao,
        washerDao: WasherDao,
        serviceDao: ServiceDao
    ): OrderUseCase {
        return OrderUseCase(
            orderApi,
            appDataStoreManager,
            orderDao,
            washerDao,
            serviceDao
        )
    }
}