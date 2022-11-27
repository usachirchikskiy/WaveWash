package com.example.wavewash.domain.validation_use_case.order

import com.example.wavewash.R
import com.example.wavewash.domain.model.Service
import com.example.wavewash.domain.validation_use_case.ValidationResult

class ValidationOrderServices {
    fun execute(services: List<Service>): ValidationResult {
        if(services.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.choose_service
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}