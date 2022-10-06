package com.example.wavewash.di

import android.app.Application
import android.provider.SyncStateContract
import com.example.wavewash.data.datastore.AppDataStore
import com.example.wavewash.data.datastore.AppDataStoreManager
import com.example.wavewash.data.remote.SillyApi
import com.example.wavewash.utils.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDataStoreManager(
        application: Application
    ): AppDataStore {
        return AppDataStoreManager(application)
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gsonBuilder: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
    }

    @Singleton
    @Provides
    fun provideSillyApiService(retrofitBuilder: Retrofit.Builder): SillyApi {
        return retrofitBuilder
            .build()
            .create(SillyApi::class.java)
    }

}