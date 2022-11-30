package com.example.wavewash.di.auth

import com.example.wavewash.data.datastore.AppDataStore
import com.example.wavewash.data.remote.SillyWashApi
import com.example.wavewash.domain.use_cases.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

    @Provides
    @ViewModelScoped
    fun provideLogin(
        service: SillyWashApi,
        appDataStoreManager: AppDataStore,
    ): LoginUseCase {
        return LoginUseCase(
            service,
            appDataStoreManager
        )
    }
}