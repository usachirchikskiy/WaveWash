package com.example.wavewash.di.validation

import com.example.wavewash.domain.validation_use_case.order.*
import com.example.wavewash.domain.validation_use_case.service.ValidationServiceName
import com.example.wavewash.domain.validation_use_case.service.ValidationServicePrice
import com.example.wavewash.domain.validation_use_case.service.ValidationServiceTime
import com.example.wavewash.domain.validation_use_case.washer.ValidationJanitorName
import com.example.wavewash.domain.validation_use_case.washer.ValidationJanitorStake
import com.example.wavewash.domain.validation_use_case.washer.ValidationJanitorTelephone
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ValidationModule {

    @Provides
    @ViewModelScoped
    fun provideValidationJanitorName(): ValidationJanitorName {
        return ValidationJanitorName()
    }

    @Provides
    @ViewModelScoped
    fun provideValidationJanitorTelephone(): ValidationJanitorTelephone {
        return ValidationJanitorTelephone()
    }

    @Provides
    @ViewModelScoped
    fun provideValidationJanitorStake(): ValidationJanitorStake {
        return ValidationJanitorStake()
    }

    @Provides
    @ViewModelScoped
    fun provideValidationServiceName(): ValidationServiceName {
        return ValidationServiceName()
    }

    @Provides
    @ViewModelScoped
    fun provideValidationServicePrice(): ValidationServicePrice {
        return ValidationServicePrice()
    }

    @Provides
    @ViewModelScoped
    fun provideValidationServiceTime(): ValidationServiceTime {
        return ValidationServiceTime()
    }

    @Provides
    @ViewModelScoped
    fun provideValidationOrderCarNumber(): ValidationOrderCarNumber {
        return ValidationOrderCarNumber()
    }

    @Provides
    @ViewModelScoped
    fun provideValidationOrderServices(): ValidationOrderServices {
        return ValidationOrderServices()
    }

    @Provides
    @ViewModelScoped
    fun provideValidationOrderWashers(): ValidationOrderWashers {
        return ValidationOrderWashers()
    }

    @Provides
    @ViewModelScoped
    fun provideValidationClientName(): ValidationClientName {
        return ValidationClientName()
    }

    @Provides
    @ViewModelScoped
    fun provideValidationClientTelephoneNumber(): ValidationClientTelephoneNumber {
        return ValidationClientTelephoneNumber()
    }
}