package com.example.wavewash.domain.validation_use_case.service

import com.example.wavewash.R
import com.example.wavewash.domain.validation_use_case.ValidationResult

class ValidationServiceName {
    fun execute(name: String): ValidationResult {
        if(name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.required_field
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}