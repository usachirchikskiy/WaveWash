package com.example.wavewash.di

import android.app.Application
import androidx.room.Room
import com.example.wavewash.data.datastore.AppDataStore
import com.example.wavewash.data.datastore.AppDataStoreManager
import com.example.wavewash.data.local.SillyWashDatabase
import com.example.wavewash.data.remote.SillyWashApi
import com.example.wavewash.domain.validation_use_case.*
import com.example.wavewash.domain.validation_use_case.service.ValidationServiceName
import com.example.wavewash.domain.validation_use_case.service.ValidationServicePrice
import com.example.wavewash.domain.validation_use_case.service.ValidationServiceTime
import com.example.wavewash.domain.validation_use_case.washer.ValidationJanitorName
import com.example.wavewash.domain.validation_use_case.washer.ValidationJanitorStake
import com.example.wavewash.domain.validation_use_case.washer.ValidationJanitorTelephone
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

    @Provides
    @Singleton
    fun provideWashDatabase(app: Application): SillyWashDatabase {
        return Room.databaseBuilder(
            app,
            SillyWashDatabase::class.java,
            "silly_wash.db"
        ).build()
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
    fun provideSillyApiService(retrofitBuilder: Retrofit.Builder): SillyWashApi {
        return retrofitBuilder
            .build()
            .create(SillyWashApi::class.java)
    }

}